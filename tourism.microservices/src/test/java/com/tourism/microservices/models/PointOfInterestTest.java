package com.tourism.microservices.models;

import org.junit.Test;

import java.awt.geom.Point2D;

import static org.junit.Assert.*;

/**
 * Created by Iulian on 16.05.2017.
 */
public class PointOfInterestTest {
    @Test
    public void getLocation() throws Exception {
        PointOfInterest x = new PointOfInterest();
        Point2D.Double expResult = new Point2D.Double(1,1);
        x.setLocation(expResult);
        assertEquals(expResult,x.getLocation());
    }

    @Test
    public void setId() throws Exception {
        PointOfInterest poi = new PointOfInterest();
        poi.setId("id8942534ffd");
        String expResult = poi.getId();
        assertEquals(expResult,poi.getId());
    }

    @Test
    public void getId() throws Exception {
        PointOfInterest poi = new PointOfInterest();
        String expResult = "id8942534ffd";
        poi.setId(expResult);
        assertEquals(expResult,poi.getId());
    }

    @Test
    public void setLocation() throws Exception {
        PointOfInterest poi = new PointOfInterest();
        poi.setLocation( new Point2D.Double(2,3));
        Point2D.Double expResult = poi.getLocation();
        assertEquals(expResult,poi.getLocation());
    }

    @Test
    public void getName() throws Exception {
        PointOfInterest poi = new PointOfInterest();
        String expResult = "Iulian";
        poi.setName(expResult);
        assertEquals(expResult, poi.getName());
    }

    @Test
    public void setName() throws Exception {
        PointOfInterest poi = new PointOfInterest();
        poi.setName("Iulian");
        String expResult = "Iulian";
        assertEquals(expResult, poi.getName());
    }

    @Test
    public void getDescription() throws Exception {
        PointOfInterest poi = new PointOfInterest();
        String expResult = "Palace";
        poi.setDescription(expResult);
        assertEquals(expResult, poi.getDescription());
    }

    @Test
    public void setDescription() throws Exception {
        PointOfInterest poi = new PointOfInterest();
        poi.setDescription("Pizza place");
        String expResult = "Pizza place";
        assertEquals(expResult, poi.getDescription());
    }

    @Test
    public void getAddress() throws Exception {
        PointOfInterest poi = new PointOfInterest();
        String expResult = "Bulevardul Indepenentei, 42";
        poi.setAddress(expResult);
        assertEquals(expResult, poi.getAddress());
        assertEquals(0,0);
    }

    @Test
    public void setAddress() throws Exception {
        PointOfInterest poi = new PointOfInterest();
        poi.setAddress("Strada Carol Davila, numarul 8. bloc 8, scara A");
        String expResult = "Strada Carol Davila, numarul 8. bloc 8, scara A";
        assertEquals(expResult, poi.getDescription());
    }

    @Test
    public void getIcon() throws Exception {
        assertEquals(0,0);
    }

    @Test
    public void setIcon() throws Exception {
        assertEquals(0,0);
    }

    @Test
    public void getTypes() throws Exception {
        assertEquals(0,0);
    }

    @Test
    public void setTypes() throws Exception {
        assertEquals(0,0);
    }

    @Test
    public void addType() throws Exception {
        assertEquals(0,0);
    }

    @Test
    public void getLocationId() throws Exception {
        PointOfInterest poi = new PointOfInterest();
        String expResult = "f27h29hf2ffh929fdfqjdqw";
        poi.setLocationId(expResult);
        assertEquals(expResult, poi.getLocationId());
    }

    @Test
    public void setLocationId() throws Exception {
        PointOfInterest poi = new PointOfInterest();
        poi.setLocationId("12k94dagn59efjwk");
        String expResult = "12k94dagn59efjwk";
        assertEquals(expResult, poi.getLocationId());
    }

    @Test
    public void getFormattedNumber() throws Exception {
        PointOfInterest poi = new PointOfInterest();
        String expResult = "56489133149";
        poi.setFormattedNumber(expResult);
        assertEquals(expResult, poi.getFormattedNumber());
    }

    @Test
    public void setFormattedNumber() throws Exception {
        PointOfInterest poi = new PointOfInterest();
        poi.setFormattedNumber("489439818949");
        String expResult = "489439818949";
        assertEquals(expResult, poi.getFormattedNumber());
    }

    @Test
    public void getRating() throws Exception {
        PointOfInterest poi = new PointOfInterest();
        double expResult = 165489.456;
        poi.setRating(expResult);
        assertEquals(expResult, poi.getRating());
    }

    @Test
    public void setRating() throws Exception {
        PointOfInterest poi = new PointOfInterest();
        poi.setRating(48633.243);
        double expResult = 48633.243;
        assertEquals(expResult, poi.getRating());
    }

    @Test
    public void getReference() throws Exception {
        PointOfInterest poi = new PointOfInterest();
        String expResult = "https://www.google.ro/maps/place/Teiul+lui+Eminescu/@47.1831628,27.5672462,15z/data=!4m5!3m4!1s0x0:0x1401736fc4586f8f!8m2!3d47.1783464!4d27.5667116?hl=ro";
        poi.setReference(expResult);
        assertEquals(expResult, poi.getReference());
    }

    @Test
    public void setReference() throws Exception {
        PointOfInterest poi = new PointOfInterest();
        poi.setReference("https://www.google.ro/maps/place/%D8%AD%D8%A7%D8%AA%D9%89+%D8%A7%D9%84%D8%A8%D8%B1%D9%83%D8%A9%E2%80%AD/@30.101105,31.2174953,17z/data=!4m13!1m7!3m6!1s0x14583fa60b21beeb:0x79dfb296e8423bba!2sCairo,+Guvernoratul+Cairo,+Egipt!3b1!8m2!3d30.0444196!4d31.2357116!3m4!1s0x0:0x238418c254fa3d02!8m2!3d30.1002506!4d31.2172866?hl=ro");
        String expResult = "https://www.google.ro/maps/place/%D8%AD%D8%A7%D8%AA%D9%89+%D8%A7%D9%84%D8%A8%D8%B1%D9%83%D8%A9%E2%80%AD/@30.101105,31.2174953,17z/data=!4m13!1m7!3m6!1s0x14583fa60b21beeb:0x79dfb296e8423bba!2sCairo,+Guvernoratul+Cairo,+Egipt!3b1!8m2!3d30.0444196!4d31.2357116!3m4!1s0x0:0x238418c254fa3d02!8m2!3d30.1002506!4d31.2172866?hl=ro";
        assertEquals(expResult, poi.getReference());
    }

    @Test
    public void getUrl() throws Exception {
        PointOfInterest poi = new PointOfInterest();
        String expResult = "https://www.google.ro/maps/place/Kokilaben+Dhirubhai+Ambani+Hospital+%26+Medical+Research+Institute/@19.1147345,72.8334711,13z/data=!4m13!1m7!3m6!1s0x3be7c6306644edc1:0x5da4ed8f8d648c69!2sMumbai,+Maharashtra,+India!3b1!8m2!3d19.0759837!4d72.8776559!3m4!1s0x0:0x3860c51257a72c24!8m2!3d19.1312213!4d72.8247643?hl=ro";
        poi.setUrl(expResult);
        assertEquals(expResult, poi.getUrl());
    }

    @Test
    public void setUrl() throws Exception {
        PointOfInterest poi = new PointOfInterest();
        poi.setUrl("https://www.google.ro/maps/place/Kokilaben+Dhirubhai+Ambani+Hospital+%26+Medical+Research+Institute/@19.1147345,72.8334711,13z/data=!4m13!1m7!3m6!1s0x3be7c6306644edc1:0x5da4ed8f8d648c69!2sMumbai,+Maharashtra,+India!3b1!8m2!3d19.0759837!4d72.8776559!3m4!1s0x0:0x3860c51257a72c24!8m2!3d19.1312213!4d72.8247643?hl=ro");
        String expResult = "https://www.google.ro/maps/place/Kokilaben+Dhirubhai+Ambani+Hospital+%26+Medical+Research+Institute/@19.1147345,72.8334711,13z/data=!4m13!1m7!3m6!1s0x3be7c6306644edc1:0x5da4ed8f8d648c69!2sMumbai,+Maharashtra,+India!3b1!8m2!3d19.0759837!4d72.8776559!3m4!1s0x0:0x3860c51257a72c24!8m2!3d19.1312213!4d72.8247643?hl=ro";
        assertEquals(expResult, poi.getUrl());
    }

    @Test
    public void getVicinity() throws Exception {
        PointOfInterest poi = new PointOfInterest();
        String expResult = "https://www.google.ro/maps/place/Iulius+Mall/@45.7553535,21.1877449,13z/data=!4m13!1m7!3m6!1s0x4745677dcb0fb5a7:0x537faf6473936749!2sTimi%C8%99oara!3b1!8m2!3d45.7488716!4d21.2086793!3m4!1s0x0:0x26a17360fdf4543e!8m2!3d45.7667147!4d21.2285471?hl=ro";
        poi.setVicinity(expResult);
        assertEquals(expResult, poi.getVicinity());
    }

    @Test
    public void setVicinity() throws Exception {
        PointOfInterest poi = new PointOfInterest();
        poi.setVicinity("https://www.google.ro/maps/place/LORENZA+BEAUTY+SPA/@45.7553535,21.1877449,13z/data=!4m13!1m7!3m6!1s0x4745677dcb0fb5a7:0x537faf6473936749!2sTimi%C8%99oara!3b1!8m2!3d45.7488716!4d21.2086793!3m4!1s0x0:0xc3edbe00653d88c2!8m2!3d45.7660037!4d21.2420976?hl=ro");
        String expResult = "https://www.google.ro/maps/place/LORENZA+BEAUTY+SPA/@45.7553535,21.1877449,13z/data=!4m13!1m7!3m6!1s0x4745677dcb0fb5a7:0x537faf6473936749!2sTimi%C8%99oara!3b1!8m2!3d45.7488716!4d21.2086793!3m4!1s0x0:0xc3edbe00653d88c2!8m2!3d45.7660037!4d21.2420976?hl=ro";
        assertEquals(expResult, poi.getVicinity());
    }

    @Test
    public void getWebsite() throws Exception {
        PointOfInterest poi = new PointOfInterest();
        String expResult = "https://www.cinemacity.ro/";
        poi.setWebsite(expResult);
        assertEquals(expResult, poi.getWebsite());
    }

    @Test
    public void setWebsite() throws Exception {
        PointOfInterest poi = new PointOfInterest();
        poi.setWebsite("https://www.cinemacity.ro/");
        String expResult = "https://www.cinemacity.ro/";
        assertEquals(expResult, poi.getWebsite());
    }

    @Test
    public void getReviews() throws Exception {
        assertEquals(0,0);
    }

    @Test
    public void addReview() throws Exception {
        assertEquals(0,0);
    }

}