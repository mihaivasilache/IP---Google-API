package com.tourism.microservices.dispatcher.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tourism.microservices.dispatcher.models.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Created by apiriu on 5/9/2017.
 */
@RestController
@RequestMapping("")
public class DispatcherController {

    @RequestMapping(value = "/pointsofinterest", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getNearbyPOIs(@RequestParam(value = "lat", required = true) Float latitude,
                                                @RequestParam(value = "lng", required = true) Float longitude,
                                                @RequestParam(value = "radius", required = true) Integer radius,
                                                @RequestHeader(value = "types", required = false) String poiTypes)
    {
        String serviceUrl = "http://localhost:5113/pointsofinterest?lat=%s&lng=%s&radius=%s";
        try {
            serviceUrl = String.format(serviceUrl, latitude.toString(), longitude.toString(), radius.toString());
            System.out.println(serviceUrl);
            URL obj = new URL(serviceUrl);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("GET");
            if(poiTypes != null && poiTypes.length() > 0) {
                conn.setRequestProperty("types", poiTypes);
            }
            conn.setDoInput(true);
            conn.setDoOutput(true);

            int status = conn.getResponseCode();
            BufferedReader br;
            if (200 <= status && status <= 299) {
                br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                StringBuilder buffer = new StringBuilder();
                int read;
                char[] chars = new char[1024];
                while ((read = br.read(chars)) != -1)
                    buffer.append(chars, 0, read);
                JSONArray json = null;
                try {
                    json = new JSONArray(buffer.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ObjectMapper mapper = new ObjectMapper();
                com.fasterxml.jackson.databind.node.ObjectNode result = mapper.createObjectNode();
                JsonNode jsonNode = mapper.readTree(json.toString());
                result.set("array", jsonNode);

                return new ResponseEntity<Object>(jsonNode, HttpStatus.OK);
            } else {
                br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
                StringBuilder buffer = new StringBuilder();
                int read;
                char[] chars = new char[1024];
                while ((read = br.read(chars)) != -1)
                    buffer.append(chars, 0, read);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = {"/pointsofinterest/{placeid}", "/pointsofinterest/{placeid}/{key}"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getPOIDetails(@PathVariable(value = "placeid") String placeId,
                                                @PathVariable(value = "key", required = false) String key) {
        String serviceUrl = "http://localhost:5113/pointsofinterest/%s";
        try {
            serviceUrl = String.format(serviceUrl, placeId);
            if(key != null && key.length() > 0) {
                serviceUrl += "/" + key;
            }
            URL obj = new URL(serviceUrl);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            int status = conn.getResponseCode();
            BufferedReader br;
            if (200 <= status && status <= 299) {
                br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                StringBuilder buffer = new StringBuilder();
                int read;
                char[] chars = new char[1024];
                while ((read = br.read(chars)) != -1)
                    buffer.append(chars, 0, read);
                JSONObject json = null;
                try {
                    json = new JSONObject(buffer.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ObjectMapper mapper = new ObjectMapper();
                com.fasterxml.jackson.databind.node.ObjectNode result = mapper.createObjectNode();
                JsonNode jsonNode = mapper.readTree(json.toString());
                result.set("object", jsonNode);

                return new ResponseEntity<Object>(jsonNode, HttpStatus.OK);
            } else {
                br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
                StringBuilder buffer = new StringBuilder();
                int read;
                char[] chars = new char[1024];
                while ((read = br.read(chars)) != -1)
                    buffer.append(chars, 0, read);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/maps/points", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> getPOIMap(@RequestBody List<PointOfInterest> poisList)
    {
        String serviceUrl = "http://localhost:5111/maps/points";
        try {
            URL obj = new URL(serviceUrl);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf8");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(poisList);
            wr.write(json);
            wr.close();

            int status = conn.getResponseCode();
            BufferedReader br;
            if (200 <= status && status <= 299) {
                br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                StringBuilder buffer = new StringBuilder();
                int read;
                char[] chars = new char[1024];
                while ((read = br.read(chars)) != -1)
                    buffer.append(chars, 0, read);

                return new ResponseEntity<>(buffer.toString(), HttpStatus.OK);
            } else
            {
                br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
                StringBuilder buffer = new StringBuilder();
                int read;
                char[] chars = new char[1024];
                while ((read = br.read(chars)) != -1)
                    buffer.append(chars, 0, read);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/maps/route", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> getRouteMap(@RequestBody List<RouteStep> stepList)
    {
        String serviceUrl = "http://localhost:5111/maps/route";
        try {
            URL obj = new URL(serviceUrl);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json; charset=utf8");
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(stepList);
            wr.write(json);
            wr.close();

            int status = conn.getResponseCode();
            BufferedReader br;
            if (200 <= status && status <= 299) {
                br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                StringBuilder buffer = new StringBuilder();
                int read;
                char[] chars = new char[1024];
                while ((read = br.read(chars)) != -1)
                    buffer.append(chars, 0, read);

                return new ResponseEntity<>(buffer.toString(), HttpStatus.OK);
            } else
            {
                br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
                StringBuilder buffer = new StringBuilder();
                int read;
                char[] chars = new char[1024];
                while ((read = br.read(chars)) != -1)
                    buffer.append(chars, 0, read);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/route/{travel_mode}", method = RequestMethod.POST)
    @ResponseBody
        public ResponseEntity<Object> getRouteSteps(@PathVariable(value = "travel_mode") String travelOption,
                                                @RequestBody List<PointOfInterest> poisList)
    {
        String serviceUrl = "http://localhost:5112/route/" + travelOption;
        try {
            URL obj = new URL(serviceUrl);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json; charset=utf8");
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String jsonn = ow.writeValueAsString(poisList);
            wr.write(jsonn);
            wr.close();

            int status = conn.getResponseCode();
            BufferedReader br;
            if (200 <= status && status <= 299) {
                br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                StringBuilder buffer = new StringBuilder();
                int read;
                char[] chars = new char[1024];
                while ((read = br.read(chars)) != -1)
                    buffer.append(chars, 0, read);
                JSONArray json = null;
                try {
                    json = new JSONArray(buffer.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ObjectMapper mapper = new ObjectMapper();
                com.fasterxml.jackson.databind.node.ObjectNode result = mapper.createObjectNode();
                JsonNode jsonNode = mapper.readTree(json.toString());
                result.set("array", jsonNode);

                return new ResponseEntity<>(jsonNode, HttpStatus.OK);
            } else
            {
                br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
                StringBuilder buffer = new StringBuilder();
                int read;
                char[] chars = new char[1024];
                while ((read = br.read(chars)) != -1)
                    buffer.append(chars, 0, read);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/taxis", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getTaxis(@RequestParam(value = "lat", required = true) Float latitude,
                                           @RequestParam(value = "lng", required = true) Float longitude,
                                           @RequestParam(value = "radius", required = true) Integer radius) {
        String serviceUrl = "http://localhost:5112/taxis?lat=%s&lng=%s&radius=%s";
        try {
            serviceUrl = String.format(serviceUrl, latitude.toString(), longitude.toString(), radius.toString());
            URL obj = new URL(serviceUrl);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            int status = conn.getResponseCode();
            BufferedReader br;
            if (200 <= status && status <= 299) {
                br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                StringBuilder buffer = new StringBuilder();
                int read;
                char[] chars = new char[1024];
                while ((read = br.read(chars)) != -1)
                    buffer.append(chars, 0, read);
                JSONArray json = null;
                try {
                    json = new JSONArray(buffer.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ObjectMapper mapper = new ObjectMapper();
                com.fasterxml.jackson.databind.node.ObjectNode result = mapper.createObjectNode();
                JsonNode jsonNode = mapper.readTree(json.toString());
                result.set("array", jsonNode);

                return new ResponseEntity<Object>(jsonNode, HttpStatus.OK);
            } else {
                br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
                StringBuilder buffer = new StringBuilder();
                int read;
                char[] chars = new char[1024];
                while ((read = br.read(chars)) != -1)
                    buffer.append(chars, 0, read);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
