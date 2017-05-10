package com.tourism.microservices.transportation.models;

import java.awt.geom.Point2D;

/**
 * Created by gbalan on 5/9/2017.
 */
public class RouteStep {
    private Point2D.Double startLocation;
    private Point2D.Double endLocation;
    private String instructions;
    private String distance;
    private String duration;
    private String travelMode;

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public Point2D.Double getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Point2D.Double startLocation) {
        this.startLocation = startLocation;
    }

    public Point2D.Double getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Point2D.Double endLocation) {
        this.endLocation = endLocation;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
