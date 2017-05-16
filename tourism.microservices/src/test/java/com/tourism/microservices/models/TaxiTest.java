package com.tourism.microservices.models;

import com.tourism.microservices.models.Taxi;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.ResponseEntity;
import java.awt.geom.Point2D;

import static org.junit.Assert.*;

/**
 * Created by beibl on 16-May-17.
 */
public class TaxiTest {
    private Taxi taxi;
    @AfterEach
    public void tearDown() throws Exception {
        taxi=null;
    }

    @Test
    public void testGetLocation(){
        taxi=new Taxi();
        Point2D.Double point=new Point2D.Double(33,22);
        taxi.setLocation(point);
        Point2D.Double location=taxi.getLocation();
        assertTrue(location.equals(point));
    }
    @Test
    public void testSetLocation(){
        taxi=new Taxi();
        Point2D.Double point=new Point2D.Double(23,22);
        taxi.setLocation(point);
        Point2D.Double location=taxi.getLocation();
        assertTrue(location.equals(point));
    }
    @Test
    public void testGetTaxiName(){
        taxi=new Taxi();
        String taxiname="14222";
        taxi.setTaxiName(taxiname);
        String name=taxi.getTaxiName();
        assertTrue(name.equals(taxiname));
    }
    @Test
    public void testSetTaxiName(){
        taxi=new Taxi();
        String taxiname="14222";
        taxi.setTaxiName(taxiname);
        String name=taxi.getTaxiName();
        assertTrue(name.equals(taxiname));
    }
    @Test
    public void testGetCity(){
        taxi=new Taxi();
        String city="Mombasa";
        taxi.setCity(city);
        String name=taxi.getCity();
        assertTrue(name.equals(city));
    }
    @Test
    public void testSetCity(){
        taxi=new Taxi();
        String city="Mombasa";
        taxi.setCity(city);
        String name=taxi.getCity();
        assertTrue(name.equals(city));
    }
    @Test
    public void testGetPhoneNumber(){
        taxi=new Taxi();
        String phone="078896545";
        taxi.setPhnoeNumber(phone);
        String number=taxi.getPhnoeNumber();
        assertTrue(number.equals(phone));
    }
    @Test
    public void testSetPhoneNumber(){
        taxi=new Taxi();
        String phone="078896545";
        taxi.setPhnoeNumber(phone);
        String number=taxi.getPhnoeNumber();
        assertTrue(number.equals(phone));
    }
    @Test
    public void testGetPrice(){
        taxi=new Taxi();
        int price=23;
        taxi.setPrice(price);
        int rprice=taxi.getPrice();
        assertTrue(rprice==price);
    }
    @Test
    public void testSetPrice(){
        taxi=new Taxi();
        int price=23;
        taxi.setPrice(price);
        int rprice=taxi.getPrice();
        assertTrue(rprice==price);
    }
    @Test
    public void testGetRating(){
        taxi=new Taxi();
        float rating=(float)23.23;
        taxi.setRating(rating);
        float rrating=taxi.getRating();
        assertTrue(rating==rrating);
    }
    @Test
    public void testSetRating(){
        taxi=new Taxi();
        float rating=(float)23.23;
        taxi.setRating(rating);
        float rrating=taxi.getRating();
        assertTrue(rating==rrating);
    }
}