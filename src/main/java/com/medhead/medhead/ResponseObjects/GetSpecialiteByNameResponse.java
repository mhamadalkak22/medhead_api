package com.medhead.medhead.ResponseObjects;

import java.util.List;

import com.medhead.medhead.entities.Specialite;

public class GetSpecialiteByNameResponse extends BaseResponse{

    private List<Specialite> specs;

    public List<Specialite> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Specialite> specs) {
        this.specs = specs;
    }    
}
