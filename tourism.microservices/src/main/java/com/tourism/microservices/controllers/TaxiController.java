package com.tourism.microservices.controllers;

import ch.qos.logback.classic.pattern.SyslogStartConverter;
import com.tourism.microservices.models.Taxi;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.tourism.microservices.controllers.PointOfInterestController.readUrl;

/**
 * Created by Mihai-Home on 01/05/2017.
 */
@RestController
@RequestMapping("taxis")
public class TaxiController {
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Taxi>> get(@RequestParam(value = "lat", required = true) Float latitude,
                                          @RequestParam(value = "lng", required = true) Float longitude,
                                          @RequestParam(value = "radius", required = true) Integer radius) {
        if (latitude == null || longitude == null || radius == null || radius <= 0) {
            return new ResponseEntity<List<Taxi>>(HttpStatus.NOT_ACCEPTABLE);
        }
        String apiKey = "AIzaSyDXYDYmpNXAo01aw71oMT6KJXoI1aTTyvg";
        //String requestLink = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
        //        "json?location=%s,%s&radius=%s&type=taxi_stand&sensor=false&language=en&key=" + apiKey;
        String requestLink = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
                "json?location=%s,%s&radius=%s&keyword=taxi&language=en&key=" + apiKey;
        String nextPageLink = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?pagetoken=%s&key=" + apiKey;
        requestLink = String.format(requestLink, latitude.toString(), longitude.toString(), radius.toString());
        List<Taxi> pois = new ArrayList<Taxi>();
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
                    if (!"OK".equals(status)) {
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
                    String poiDetailUrl = "https://maps.googleapis.com/maps/api/place/details/json?placeid=%s&language=en&key=AIzaSyCWAxJGAVwwJG4ugVA7BZX-1QHUQ2XwkVU";
                    for (int index = 0; index < resultList.length(); index++) {
                        JSONObject poi = resultList.getJSONObject(index);
                        String tempPlaceId;
                        if (!poi.isNull("place_id")) {
                            tempPlaceId = poi.getString("place_id");
                            try {
                                JSONObject poiDetail = new JSONObject(readUrl(String.format(poiDetailUrl, tempPlaceId)));
                                poiDetail = poiDetail.getJSONObject("result");
                                String toCheck = "[\"point_of_interest\",\"establishment\"]";
                                String types = poiDetail.getString("types");
                                if(types.equals(toCheck)) {
                                    Taxi tempTaxi = new Taxi();
                                    JSONObject location = poiDetail.getJSONObject("geometry");
                                    location = location.getJSONObject("location");
                                    Point2D.Double poiLocation = new Point2D.Double();
                                    poiLocation.setLocation(location.getDouble("lat"), location.getDouble("lng"));
                                    tempTaxi.setLocation(poiLocation); // location set, latitude and longitude
                                    if (!poiDetail.isNull("name")) {
                                        tempTaxi.setTaxiName(poiDetail.getString("name"));
                                    }
                                    if (!poiDetail.isNull("formatted_phone_number")) {
                                        tempTaxi.setPhnoeNumber(poiDetail.getString("formatted_phone_number"));
                                    }
                                    if (!poiDetail.isNull("rating")) {
                                        tempTaxi.setRating((float) poiDetail.getDouble("rating"));
                                    }
                                    pois.add(tempTaxi);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println(e.toString());
                                found = false;
                            }
                        }
                    }

                    nextPageToken = (String) obj.get("next_page_token");
                    obj = new JSONObject(readUrl(String.format(nextPageLink, nextPageToken)));
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                    found = false;
                }
            }
            pois.sort(new Comparator<Taxi>() {
                @Override
                public int compare(Taxi o1, Taxi o2) {
                    if(o1.getRating() > o2.getRating())
                        return -1;
                    else return 1;
                }
            });
            return new ResponseEntity<List<Taxi>>(pois, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<Taxi>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
