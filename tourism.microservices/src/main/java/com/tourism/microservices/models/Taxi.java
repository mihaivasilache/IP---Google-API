package com.tourism.microservices.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mihai-Home on 01/05/2017.
 */
public class Taxi implements Serializable
{
    private String taxiName;
    private String city;
    private String phnoeNumber;
    private Integer price;
    private Float rating;

    public Taxi()
    {
        taxiName = "";
        city = "";
        phnoeNumber = "";
        price = 0;
    }

    public String getTaxiName()
    {
        return taxiName;
    }

    public void setTaxiName(String taxiName)
    {
        this.taxiName = taxiName;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getPhnoeNumber()
    {
        return phnoeNumber;
    }

    public void setPhnoeNumber(String phnoeNumber)
    {
        this.phnoeNumber = phnoeNumber;
    }

    public Integer getPrice()
    {
        return price;
    }

    public void setPrice(Integer price)
    {
        this.price = price;
    }

    public Float getRating()
    {
        return rating;
    }

    public void setRating(Float rating)
    {
        this.rating = rating;
    }
}
