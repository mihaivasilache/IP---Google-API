package com.tourism.microservices.controllers;

import com.tourism.microservices.models.PointOfInterest;
import com.tourism.microservices.models.RouteStep;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Iulian on 16.05.2017.
 */
class CreateRouteControllerTest {
    @Test
    void get() {
        String travel_mode = "DRIVING";
        List<PointOfInterest> allPoints = new ArrayList<PointOfInterest>();
        PointOfInterest ps = new PointOfInterest();
        ps.setLocationId("ChIJ9T_5iuTKj4ARe3GfygqMnbk");
        ps.setName("San Jose");
        ps.setAddress(null);
        ps.setDescription(null);
        ps.setIcon("https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png");
        Point2D.Double p2d = new Point2D.Double();
        p2d.setLocation(37.3382082, -121.8863286);
        ps.setLocation(p2d);

        allPoints.add(ps);

        ps = new PointOfInterest();
        ps.setLocationId("ChIJ9T_5iuTKj4ARe3GfygqMnbk");
        ps.setName("San Jose");
        ps.setAddress(null);
        ps.setDescription(null);
        ps.setIcon("https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png");
        p2d = new Point2D.Double();
        p2d.setLocation(37.3382082, -121.8863286);
        ps.setLocation(p2d);

        allPoints.add(ps);

        CreateRouteController routeControllerTester = new CreateRouteController();
        ResponseEntity<List<RouteStep>> response;
        response = routeControllerTester.get(travel_mode, allPoints);
        List<RouteStep> responseList;
        responseList = response.getBody();

        List<RouteStep> expectedSteps = new ArrayList<RouteStep>();
        RouteStep tempStep = new RouteStep();
        tempStep.setDistance("1 ft");
        tempStep.setDuration("1 min");
        Point2D.Double tempPoint = new Point2D.Double();
        tempPoint.setLocation(37.3382088, -121.8863279);
        tempStep.setEndLocation(tempPoint);
        tempPoint = new Point2D.Double();
        tempPoint.setLocation(37.3382088, -121.8863279);
        tempStep.setStartLocation(tempPoint);
        tempStep.setTravelMode("DRIVING");
        tempStep.setInstructions("Head on \u003cb\u003eN 5th St\u003c/b\u003e");

        expectedSteps.add(tempStep);

        assertEquals(expectedSteps.size(), responseList.size());
        int counter = 0;
        for (counter = 0; counter < expectedSteps.size(); counter ++)
        {
            assertEquals(expectedSteps.get(counter).getDistance(), responseList.get(counter).getDistance());
            assertEquals(expectedSteps.get(counter).getDuration(), responseList.get(counter).getDuration());
            assertEquals(expectedSteps.get(counter).getInstructions(), responseList.get(counter).getInstructions());
            assertEquals(expectedSteps.get(counter).getTravelMode(), responseList.get(counter).getTravelMode());
            assertEquals(expectedSteps.get(counter).getEndLocation(), responseList.get(counter).getEndLocation());
            assertEquals(expectedSteps.get(counter).getStartLocation(), responseList.get(counter).getStartLocation());

        }

        //assertEquals(expectedSteps, responseList);

    }

}