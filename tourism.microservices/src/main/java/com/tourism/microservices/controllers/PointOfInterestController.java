package com.tourism.microservices.controllers;

import com.tourism.microservices.models.PointOfInterest;
import com.tourism.microservices.models.poiReview;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.json.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Created by apiriu on 4/28/2017.
 */
@RestController
@RequestMapping("pointsofinterest")
public class PointOfInterestController {

    public static String readUrl(String urlString) throws Exception {
        //System.out.println("Url: " + urlString);
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            //System.out.println("Got response: " + buffer.toString());
            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<PointOfInterest>> get(@RequestParam(value = "lat", required = true) Float latitude,
                                                     @RequestParam(value = "lng", required = true) Float longitude,
                                                     @RequestParam(value = "radius", required = true) Integer radius,
                                                     @RequestHeader(value = "types", required = false) String poiTypes) {
        if (latitude == null || longitude == null || radius == null || radius <= 0) {
            return new ResponseEntity<List<PointOfInterest>>(HttpStatus.NOT_ACCEPTABLE);
        }
        List<String> typeList = new ArrayList<String>();
        if (poiTypes != null) {
            if (poiTypes.length() > 0) {
                typeList = Arrays.asList(poiTypes.split("\\s*,\\s*"));
            }
        }

        String apiKey = "AIzaSyDXYDYmpNXAo01aw71oMT6KJXoI1aTTyvg";
        String requestLink = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
                "json?location=%s,%s&radius=%s&language=en&key=" + apiKey;
        String nextPageLink = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?pagetoken=%s&key=" + apiKey;
        requestLink = String.format(requestLink, latitude.toString(), longitude.toString(), radius.toString());
        List<PointOfInterest> pois = new ArrayList<PointOfInterest>();

        String nextPageToken;
        try {
            boolean found = true;
            JSONObject obj = new JSONObject(readUrl(requestLink));
            while (found) {
                String status = (String) obj.get("status");
                long currentTime = System.currentTimeMillis();
                if ("ZERO_RESULTS".equals(status)) {
                    found = false;
                    break;
                }
                while (true) {
                    if (!"OK".equals(status)) { // try to get the link again for 10 seconds
                        if (System.currentTimeMillis() - 10000 - currentTime >= 0) {
                            break;
                        }
                        obj = new JSONObject(readUrl(requestLink));
                        status = (String) obj.get("status");
                    } else break;
                }
                if (!found) {
                    break;
                }
                try {
                    JSONArray resultList = obj.getJSONArray("results");
                    for (int index = 0; index < resultList.length(); index++) {
                        JSONObject poi = resultList.getJSONObject(index);
                        PointOfInterest poiObject = new PointOfInterest();

                        JSONObject location = poi.getJSONObject("geometry");
                        location = location.getJSONObject("location");
                        Point2D.Double poiLocation = new Point2D.Double();
                        poiLocation.setLocation(location.getDouble("lat"), location.getDouble("lng"));
                        poiObject.setLocation(poiLocation); // location set, latitude and longitude
                        if (!poi.isNull("name")) {
                            poiObject.setName(poi.getString("name"));
                        }
                        if (!poi.isNull("icon")) {
                            poiObject.setIcon(poi.getString("icon"));
                        }
                        if (!poi.isNull("place_id")) {
                            poiObject.setLocationId(poi.getString("place_id"));
                        }
                        if (!poi.isNull("types")) {
                            JSONArray types = poi.getJSONArray("types");
                            for (int i = 0; i < types.length(); i++) {
                                poiObject.addType(types.getString(i));
                            }
                        }
                        if (typeList.size() > 0 && poiObject.getTypes().size() > 0) {
                            boolean foundType = false;
                            for (String type : poiObject.getTypes()) {
                                if (typeList.contains(type)) {
                                    foundType = true;
                                    break;
                                }
                            }
                            if (!foundType) {
                                continue;
                            }
                        }

                        pois.add(poiObject);
                    }

                    if (!obj.isNull("next_page_token")) {
                        nextPageToken = (String) obj.get("next_page_token");
                        obj = new JSONObject(readUrl(String.format(nextPageLink, nextPageToken)));
                    } else {
                        break;
                    }
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                    found = false;
                }
            }
            return new ResponseEntity<List<PointOfInterest>>(pois, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<PointOfInterest>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = {"/{placeid}/{key}", "/{placeid}"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getDetail(@PathVariable(value = "placeid") String placeId,
                                            @PathVariable(value = "key", required = false) String key) {
        System.out.println(placeId);
        PointOfInterest poi = new PointOfInterest();
        boolean keyIsSpecified = (key != null && !key.isEmpty());

        String poiDetailUrl = "https://maps.googleapis.com/maps/api/place/details/json?placeid=%s&language=en&key=AIzaSyCWAxJGAVwwJG4ugVA7BZX-1QHUQ2XwkVU";
        try {
            JSONObject jAnswer = new JSONObject(readUrl(String.format(poiDetailUrl, placeId)));
            JSONObject jResult = jAnswer.getJSONObject("result");
            if (!jAnswer.getString("status").equalsIgnoreCase("OK")) {
                throw new Exception("received answer from google is not valid!");
            }

            try {
                poi.setFormattedNumber(jResult.getString("international_phone_number"));
                if (keyIsSpecified && key.equalsIgnoreCase("formattedNumber"))
                    return new ResponseEntity<>(poi.getFormattedNumber(), HttpStatus.OK);
            } catch (JSONException e) {
            }
            try {
                poi.setName(jResult.getString("name"));
                if (keyIsSpecified && key.equalsIgnoreCase("name"))
                    return new ResponseEntity<>(poi.getName(), HttpStatus.OK);
            } catch (JSONException e) {
            }
            try {
                poi.setDescription(jResult.getString("scope"));
                if (keyIsSpecified && key.equalsIgnoreCase("description"))
                    return new ResponseEntity<>(poi.getDescription(), HttpStatus.OK);
            } catch (JSONException e) {
            }
            try {
                poi.setAddress(jResult.getString("formatted_address"));
                if (keyIsSpecified && key.equalsIgnoreCase("address"))
                    return new ResponseEntity<>(poi.getAddress(), HttpStatus.OK);
            } catch (JSONException e) {
            }
            try {
                poi.setIcon(jResult.getString("icon"));
                if (keyIsSpecified && key.equalsIgnoreCase("icon"))
                    return new ResponseEntity<>(poi.getIcon(), HttpStatus.OK);
            } catch (JSONException e) {
            }
            try {
                poi.setLocationId(jResult.getString("place_id"));
                if (keyIsSpecified && key.equalsIgnoreCase("locationId"))
                    return new ResponseEntity<>(poi.getLocationId(), HttpStatus.OK);
            } catch (JSONException e) {
            }

            try {
                poi.setRating(jResult.getDouble("rating"));
                if (keyIsSpecified && key.equalsIgnoreCase("rating"))
                    return new ResponseEntity<>(poi.getRating(), HttpStatus.OK);
            } catch (JSONException e) {
            }

            try {
                poi.setReference(jResult.getString("reference"));
                if (keyIsSpecified && key.equalsIgnoreCase("reference"))
                    return new ResponseEntity<>(poi.getReference(), HttpStatus.OK);
            } catch (JSONException e) {
            }

            try {
                poi.setUrl(jResult.getString("url"));
                if (keyIsSpecified && key.equalsIgnoreCase("url"))
                    return new ResponseEntity<>(poi.getUrl(), HttpStatus.OK);
            } catch (JSONException e) {
            }

            try {
                poi.setVicinity(jResult.getString("vicinity"));
                if (keyIsSpecified && key.equalsIgnoreCase("vicinity"))
                    return new ResponseEntity<>(poi.getVicinity(), HttpStatus.OK);
            } catch (JSONException e) {
            }

            try {
                poi.setWebsite(jResult.getString("website"));
                if (keyIsSpecified && key.equalsIgnoreCase("website"))
                    return new ResponseEntity<>(poi.getWebsite(), HttpStatus.OK);
            } catch (JSONException e) {
            }

            try {
                JSONObject jReviewField = jResult.getJSONObject("reviews");
                for (int i = 0; i < jResult.length(); i++) {
                    poiReview review = new poiReview();
                    review.setAuthor(jReviewField.getString("author"));
                    review.setAuthorURL(jReviewField.getString("author_url"));
                    review.setLanguage(jReviewField.getString("language"));
                    review.setRating(jReviewField.getDouble("rating"));
                    review.setText(jReviewField.getString("text"));
                    review.setTime(jReviewField.getDouble("time"));
                    poi.addReview(review);
                }
                if (keyIsSpecified && key.equalsIgnoreCase("reviews"))
                    return new ResponseEntity<>(poi.getReviews(), HttpStatus.OK);
            } catch (JSONException e) {
            }

            try {
                JSONArray jTypes = jResult.getJSONArray("types");
                for (int i = 0; i < jTypes.length(); i++) {
                    poi.addType(jTypes.getString(i));
                }
                if (keyIsSpecified && key.equalsIgnoreCase("types"))
                    return new ResponseEntity<>(poi.getTypes(), HttpStatus.OK);
            } catch (JSONException e) {
            }

            try {
                JSONObject jLocation = jResult.getJSONObject("geometry").getJSONObject("location");
                poi.setLocation(new Point2D.Double(jLocation.getDouble("lat"), jLocation.getDouble("lng")));
                if (!keyIsSpecified && key.equalsIgnoreCase(""))
                    return new ResponseEntity<>(poi.getLocation(), HttpStatus.OK);
            } catch (JSONException e) {
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(poi, HttpStatus.OK);
    }

}
