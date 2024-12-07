package com.medhead.medhead.ResponseObjects;

import com.medhead.medhead.entities.Hopital;

public class GetHospitalByIdResponse extends BaseResponse{

    private Hopital data;

    public Hopital getData() {
        return data;
    }

    public void setData(Hopital data) {
        this.data = data;
    }

    
    
}
