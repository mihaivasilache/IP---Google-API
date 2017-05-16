package com.tourism.microservices.controllers;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Iulian on 16.05.2017.
 */
public class PointOfInterestControllerTest {
    @Test
    public void readUrl() throws Exception {
        String answer = PointOfInterestController.readUrl(" https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJEwF-0537ykARaRRgtshhtW4&language=en&key=AIzaSyCWAxJGAVwwJG4ugVA7BZX-1QHUQ2XwkVU");
        String expectedAnswer = "";
        assertEquals(expectedAnswer, answer);
    }

    @Test
    public void get() throws Exception {
        assertEquals(4, 4);
    }

    @Test
    public void getDetail() throws Exception {
        assertEquals(0,0);
    }

}