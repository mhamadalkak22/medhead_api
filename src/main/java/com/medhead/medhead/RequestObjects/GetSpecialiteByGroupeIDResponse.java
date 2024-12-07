package com.medhead.medhead.RequestObjects;

import java.util.List;

import com.medhead.medhead.ResponseObjects.BaseResponse;
import com.medhead.medhead.entities.Specialite;

public class GetSpecialiteByGroupeIDResponse extends BaseResponse{


    private List<Specialite> specialites;

    public List<Specialite> getSpecialites() {
        return specialites;
    }

    public void setSpecialites(List<Specialite> specialites) {
        this.specialites = specialites;
    }
    
}
