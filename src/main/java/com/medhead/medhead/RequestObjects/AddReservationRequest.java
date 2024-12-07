package com.medhead.medhead.RequestObjects;

public class  AddReservationRequest {

    private long hopitalID;
    private String name;
    private long specID;
    public long getHopitalID() {
        return hopitalID;
    }
    public void setHopitalID(long hopitalID) {
        this.hopitalID = hopitalID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getSpecID() {
        return specID;
    }
    public void setSpecID(long specID) {
        this.specID = specID;
    }

    
    
}
