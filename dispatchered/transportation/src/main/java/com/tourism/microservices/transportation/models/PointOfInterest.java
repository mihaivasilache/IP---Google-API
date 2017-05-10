package com.tourism.microservices.transportation.models;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * Created by apiriu on 4/28/2017.
 */
public class PointOfInterest implements Serializable{
    private String id;
    private String locationId;
    private String name;
    private String description;
    private String formattedNumber;
    private String address;
    private String icon;
    //new attributes
    private double rating;
    private String reference;
    private String url;
    private String vicinity;
    private String website;
    private List<poiReview> reviews = new ArrayList<>();

    private Point2D.Double location;
    private List<String> types = new ArrayList<String>();

    public Point2D.Double getLocation() {
        return location;
    }

    public void setId(String id) {this.id = id;}

    public String getId() {return id;}

    public void setLocation(Point2D.Double location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public void addType(String type) {
        this.getTypes().add(type);
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getFormattedNumber() {
        return formattedNumber;
    }

    public void setFormattedNumber(String formattedNumber) {
        this.formattedNumber = formattedNumber;
    }

    public double getRating() {return rating;}

    public void setRating(double rating) {this.rating = rating;}

    public String getReference() {return reference;}

    public void setReference(String reference) {this.reference = reference;}

    public String getUrl() {return url;}

    public void setUrl(String url) {this.url = url;}

    public String getVicinity() {return vicinity;}

    public void setVicinity(String vicinity) {this.vicinity = vicinity;}

    public String getWebsite() {return website;}

    public void setWebsite(String website) {this.website = website;}

    public List<poiReview> getReviews() {return reviews;}

    public void addReview(poiReview review) { this.reviews.add(review);}
}
