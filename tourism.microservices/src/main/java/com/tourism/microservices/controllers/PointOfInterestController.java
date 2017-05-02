package com.tourism.microservices.controllers;

import com.tourism.microservices.models.PointOfInterest;
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
public class PointOfInterestController
{

    public static String readUrl(String urlString) throws Exception
    {
        BufferedReader reader = null;
        try
        {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally
        {
            if (reader != null)
                reader.close();
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<PointOfInterest>> get(@RequestParam(value = "lat", required = true) Float latitude,
                                                     @RequestParam(value = "lng", required = true) Float longitude,
                                                     @RequestParam(value = "radius", required = true) Integer radius)
    {
        if (latitude == null || longitude == null || radius == null || radius <= 0)
        {
            return new ResponseEntity<List<PointOfInterest>>(HttpStatus.NOT_ACCEPTABLE);
        }

        String apiKey = "AIzaSyDXYDYmpNXAo01aw71oMT6KJXoI1aTTyvg";
        String requestLink = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
                "json?location=%s,%s&radius=%s&language=en&key=" + apiKey;
        String nextPageLink = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?pagetoken=%s&key=" + apiKey;
        requestLink = String.format(requestLink, latitude.toString(), longitude.toString(), radius.toString());
        List<PointOfInterest> pois = new ArrayList<PointOfInterest>();

        String nextPageToken;
        try
        {
            boolean found = true;
            JSONObject obj = new JSONObject(readUrl(requestLink));
            while (found)
            {
                String status = (String) obj.get("status");
                long currentTime = System.currentTimeMillis();
                if ("ZERO_RESULTS".equals(status))
                {
                    found = false;
                    break;
                }
                while (true)
                {
                    if (!"OK".equals(status))
                    { // try to get the link again for 10 seconds
                        if (System.currentTimeMillis() - 10000 - currentTime >= 0)
                        {
                            break;
                        }
                        obj = new JSONObject(readUrl(requestLink));
                        status = (String) obj.get("status");
                    } else break;
                }
                if (!found)
                {
                    break;
                }
                try
                {
                    JSONArray resultList = obj.getJSONArray("results");
                    for (int index = 0; index < resultList.length(); index++)
                    {
                        JSONObject poi = resultList.getJSONObject(index);
                        PointOfInterest poiObject = new PointOfInterest();

                        JSONObject location = poi.getJSONObject("geometry");
                        location = location.getJSONObject("location");
                        Point2D.Double poiLocation = new Point2D.Double();
                        poiLocation.setLocation(location.getDouble("lat"), location.getDouble("lng"));
                        poiObject.setLocation(poiLocation); // location set, latitude and longitude
                        if (!poi.isNull("name"))
                        {
                            poiObject.setName(poi.getString("name"));
                        }
                        if (!poi.isNull("icon"))
                        {
                            poiObject.setIcon(poi.getString("icon"));
                        }
                        if (!poi.isNull("place_id"))
                        {
                            poiObject.setLocationId(poi.getString("place_id"));
                        }
                        if (!poi.isNull("types"))
                        {
                            JSONArray types = poi.getJSONArray("types");
                            for (int i = 0; i < types.length(); i++)
                            {
                                poiObject.addType(types.getString(i));
                            }
                        }
                        pois.add(poiObject);
                    }

                    nextPageToken = (String) obj.get("next_page_token");
                    obj = new JSONObject(readUrl(String.format(nextPageLink, nextPageToken)));
                } catch (Exception ex)
                {
                    System.out.println(ex.toString());
                    found = false;
                }
            }
            return new ResponseEntity<List<PointOfInterest>>(pois, HttpStatus.OK);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<List<PointOfInterest>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
