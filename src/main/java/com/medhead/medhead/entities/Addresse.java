package com.medhead.medhead.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Addresse {

    public Addresse()
    {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addresseID;

    private String display_name;
    private String icon;

    private String lat;
    private String lon;

   

    public long getAddresseID() {
        return addresseID;
    }
    public void setAddresseID(long addresseID) {
        this.addresseID = addresseID;
    }
    public String getDisplay_name() {
        return display_name;
    }
    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getLat() {
        return lat;
    }
    public void setLat(String lat) {
        this.lat = lat;
    }
    public String getLon() {
        return lon;
    }
    public void setLon(String lon) {
        this.lon = lon;
    }
    
    
    
}
