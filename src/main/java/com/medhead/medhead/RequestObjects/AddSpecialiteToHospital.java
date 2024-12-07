package com.medhead.medhead.RequestObjects;

public class AddSpecialiteToHospital {

    private long specialiteID;
    private long hopitalID;

    public long getHopitalID() {
        return hopitalID;
    }

    public void setHopitalID(long hopitalID) {
        this.hopitalID = hopitalID;
    }

    public long getSpecialiteID() {
        return specialiteID;
    }

    public void setSpecialiteID(long specialiteID) {
        this.specialiteID = specialiteID;
    }
    
}
