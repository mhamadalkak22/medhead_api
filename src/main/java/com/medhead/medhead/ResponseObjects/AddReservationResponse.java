package com.medhead.medhead.ResponseObjects;

import com.medhead.medhead.entities.Lit;

public class AddReservationResponse extends BaseResponse{

    private Lit lit;

    public Lit getLit() {
        return lit;
    }

    public void setLit(Lit lit) {
        this.lit = lit;
    }
    
}
