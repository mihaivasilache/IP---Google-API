package com.tourism.microservices.pointsofinterest.models;

/**
 * Created by Adrian on 5/9/2017.
 */
public class poiReview {
    private String author;
    private String authorURL;
    private String language;
    private double rating;
    private String text;
    private double time;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorURL() {
        return authorURL;
    }

    public void setAuthorURL(String authorURL) {
        this.authorURL = authorURL;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getRating() {
        return rating;
    }

    public void setRating (double rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getTime() {
        return time;
    }

    public void setTime (double time) {
        this.time = time;
    }
}
