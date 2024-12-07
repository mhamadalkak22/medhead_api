package com.medhead.medhead.RequestObjects;

public class GetLitBySpecAndHopitalRequest {

    private long specID;
    private long hopitalID;

    public long getHopitalID() {
        return hopitalID;
    }

    public void setHopitalID(long hopitalID) {
        this.hopitalID = hopitalID;
    }

    public long getSpecID() {
        return specID;
    }

    public void setSpecID(long specID) {
        this.specID = specID;
    }

}
