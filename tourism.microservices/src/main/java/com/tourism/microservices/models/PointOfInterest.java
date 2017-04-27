package com.tourism.microservices.models;

import javax.persistence.*;
import java.awt.*;
import java.io.Serializable;

/**
 * Created by apiriu on 4/28/2017.
 */
@Entity
@Table(name = "points_of_interests")
public class PointOfInterest implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private Point location;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 1000)
    private String shortHistory;

    @Column(length = 100)
    private String imageLink;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortHistory() {
        return shortHistory;
    }

    public void setShortHistory(String shortHistory) {
        this.shortHistory = shortHistory;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
