package com.tourism.microservices.controllers;

import com.tourism.microservices.models.Taxi;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by beibl on 16-May-17.
 */
public class TaxiControllerTest {
    private TaxiController taxiController;

    @AfterEach
    public void tearDown() throws Exception {
        taxiController=null;
    }

    @Test
    public void getShouldReturnNothing() throws Exception {

            taxiController = new TaxiController();
            float latitude = (float) 47.170;
            float longitude = (float) 27.576;
            int radius = 200;
            ResponseEntity<List<Taxi>> result = taxiController.get(latitude, longitude, radius);
            List<Taxi> body = result.getBody();
            assertTrue(body.isEmpty());

    }
    @Test
    public void getShouldReturnNotAcceptableForNullLongitude(){
        taxiController = new TaxiController();
        float latitude = (float) 47.170;
        int radius = 200;
        ResponseEntity<List<Taxi>> result = taxiController.get(latitude,null, radius);
        assertTrue(result.toString().trim().equals("<406 Not Acceptable,{}>"));
    }
    @Test
    public void getShouldReturnNotAcceptableForNullLatitude(){
        taxiController = new TaxiController();
        float longitude = (float) 27.576;
        int radius = 200;
        ResponseEntity<List<Taxi>> result = taxiController.get(null,longitude, radius);
        assertTrue(result.toString().trim().equals("<406 Not Acceptable,{}>"));
    }
    @Test
    public void getShouldReturnNotAcceptableForNullRadius(){
        taxiController = new TaxiController();
        float longitude = (float) 27.576;
        float latitude = (float) 47.170;
        ResponseEntity<List<Taxi>> result = taxiController.get(latitude,longitude, null);
        assertTrue(result.toString().trim().equals("<406 Not Acceptable,{}>"));
    }

    @Test
    public void getShouldReturnNotAcceptableForSmallRadius(){
        taxiController = new TaxiController();
        float longitude = (float) 27.576;
        float latitude = (float) 47.170;
        int radius=0;
        ResponseEntity<List<Taxi>> result = taxiController.get(latitude,longitude,radius);
        assertTrue(result.toString().trim().equals("<406 Not Acceptable,{}>"));
    }
    @Test
    public void getShouldReturnSomething() throws Exception{
        taxiController = new TaxiController();
        float latitude = (float) 41.521;
        float longitude = (float) -88.074;
        int radius = 20000;
        ResponseEntity<List<Taxi>> result = taxiController.get(latitude, longitude, radius);
        List<Taxi> body = result.getBody();
        //System.out.println(body.toString());
        assertTrue(!body.isEmpty());
    }



}