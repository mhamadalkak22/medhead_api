package com.medhead.medhead.ResponseObjects;

import java.util.ArrayList;
import java.util.List;

import com.medhead.medhead.entities.Hopital;

public class GetAllHopitalResponse extends BaseResponse {

    private List<Hopital> data;

    public GetAllHopitalResponse() {
        this.data = new ArrayList <Hopital>();
    }


    public List<Hopital> getData() {
        return data;
    }

    public void setData(List<Hopital> data) {
        this.data = data;
    }
    

}
