package com.tourism.microservices.transportation.controllers;

import com.tourism.microservices.transportation.models.*;;
import org.json.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

/**
 * Created by gbalan on 5/9/2017.
 */

@RestController
@RequestMapping("route")
public class CreateRouteController {

    public static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    @RequestMapping(value = "/{travel_mode}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<RouteStep>> get(@PathVariable(value = "travel_mode") String travelMode ,@RequestBody List<PointOfInterest> poisList)
    {
        if (!travelMode.toLowerCase().equals("driving") && !travelMode.toLowerCase().equals("walking") && !travelMode.toLowerCase().equals("bicycling") && !travelMode.toLowerCase().equals("transit"))
//            return  new ResponseEntity<>("The request must submit a travel_mode (driving, walking, bicycling, transit)", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<List<RouteStep>>(HttpStatus.BAD_REQUEST);
        if (poisList.size() != 2)
//            return  new ResponseEntity<>("The request must submit only 2 points", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<List<RouteStep>>(HttpStatus.BAD_REQUEST);
        String routeString = "https://maps.googleapis.com/maps/api/directions/json?origin=%s&destination=%s&mode=%s&key=AIzaSyCWAxJGAVwwJG4ugVA7BZX-1QHUQ2XwkVU";
        List<RouteStep> stepsList = new ArrayList<RouteStep>();
        try {
            String urlString = String.format(routeString, String.format("%f,%f", poisList.get(0).getLocation().getX(), poisList.get(0).getLocation().getY()),
                    String.format("%f,%f", poisList.get(1).getLocation().getX(), poisList.get(1).getLocation().getY()),
                    travelMode);
            System.out.println(urlString);
            JSONObject jAnswer = new JSONObject(readUrl(urlString));
            String statusCode = jAnswer.getString("status");
            if (!statusCode.equals("OK"))
            {
//                return new ResponseEntity<String>("Timed out!", HttpStatus.NO_CONTENT);
                return new ResponseEntity<List<RouteStep>>(HttpStatus.NO_CONTENT);
            }
            JSONArray jArray = jAnswer.getJSONArray("routes");
            JSONObject jResult = jArray.getJSONObject(0);
            JSONArray jRoute = jResult.getJSONArray("legs");
            jResult = jRoute.getJSONObject(0);
            JSONArray steps = jResult.getJSONArray("steps");
            for (Integer counter = 0; counter < steps.length(); counter++)
            {
                JSONObject stepObject = steps.getJSONObject(counter);
                RouteStep tempStep = new RouteStep();
                tempStep.setDistance(stepObject.getJSONObject("distance").getString("text"));
                tempStep.setDuration(stepObject.getJSONObject("duration").getString("text"));
                tempStep.setTravelMode(stepObject.getString("travel_mode"));
                tempStep.setInstructions(stepObject.getString("html_instructions"));
                Point2D.Double tempPoint = new Point2D.Double();
                tempPoint.setLocation(stepObject.getJSONObject("start_location").getDouble("lat"), stepObject.getJSONObject("start_location").getDouble("lng"));
                tempStep.setStartLocation(tempPoint);
                tempPoint = new Point2D.Double();
                tempPoint.setLocation(stepObject.getJSONObject("end_location").getDouble("lat"), stepObject.getJSONObject("end_location").getDouble("lng"));
                tempStep.setEndLocation(tempPoint);
                stepsList.add(tempStep);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<RouteStep>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<RouteStep>>(stepsList,HttpStatus.OK);
    }
}
