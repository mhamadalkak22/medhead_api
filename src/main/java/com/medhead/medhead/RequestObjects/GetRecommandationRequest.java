package com.medhead.medhead.RequestObjects;

public class GetRecommandationRequest {

    private long specID;
    private String latitude; 
    private String longitude;
    public long getSpecID() {
        return specID;
    }
    public void setSpecID(long specID) {
        this.specID = specID;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    
    
}
