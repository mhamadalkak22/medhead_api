package com.medhead.medhead.ResponseObjects;

import com.medhead.medhead.entities.Hopital;

public class GetHopitalByUserIDResponse extends BaseResponse{

    private Hopital Hopital;

    public Hopital getHopital() {
        return Hopital;
    }

    public void setHopital(Hopital hopital) {
        Hopital = hopital;
    }
    
}
