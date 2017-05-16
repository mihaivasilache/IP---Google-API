package com.tourism.microservices.controllers;

import com.tourism.microservices.models.PointOfInterest;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Iulian on 16.05.2017.
 */
public class PointOfInterestControllerTest {
    @Test
    public void readUrl() throws Exception {
        String answer = null;
        answer = PointOfInterestController.readUrl(" https://maps.googleapis.com/maps/api/place/details/json?placeid=" + "ChIJu9059nz7ykARPPUmVNqAy3w" + "&language=en&key=AIzaSyCWAxJGAVwwJG4ugVA7BZX-1QHUQ2XwkVU");
        String expected_Answer = "{\n";
        String expectedAnswer = "{\n" +
                "   \"html_attributions\" : [],\n" +
                "   \"result\" : {\n" +
                "      \"address_components\" : [\n" +
                "         {\n" +
                "            \"long_name\" : \"Iași\",\n" +
                "            \"short_name\" : \"Iași\",\n" +
                "            \"types\" : [ \"locality\", \"political\" ]\n" +
                "         },\n" +
                "         {\n" +
                "            \"long_name\" : \"Iași County\",\n" +
                "            \"short_name\" : \"IS\",\n" +
                "            \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" +
                "         },\n" +
                "         {\n" +
                "            \"long_name\" : \"Romania\",\n" +
                "            \"short_name\" : \"RO\",\n" +
                "            \"types\" : [ \"country\", \"political\" ]\n" +
                "         }\n" +
                "      ],\n";
        //assertTrue(answer.startsWith(expectedAnswer));
        assertTrue(answer.startsWith(expected_Answer));
    }

    @Test
    public void get() throws Exception {
        PointOfInterestController poiController = new PointOfInterestController();
        float lng = (float)(27.5865453);
        float lat = (float)(47.156044);
        int rad = 500;
        ResponseEntity<List<PointOfInterest>> answer = poiController.get(lat , lng , rad, null);
        List<PointOfInterest> body = answer.getBody();
        assertFalse(body.isEmpty());
    }

    @Test
    public void getDetail() throws Exception {

        PointOfInterestController poiController = new PointOfInterestController();
        ResponseEntity<Object> response = poiController.getDetail("ChIJN1t_tDeuEmsRUsoyG83frY4", "name");
        Object result = response.getBody();
        assertNotNull(result);
    }

}