package com.medhead.medhead.ResponseObjects;

import java.util.List;

import com.medhead.medhead.entities.Recommandation;

public class RecommandationResponse extends BaseResponse{

    private List<Recommandation> recommandations;

    public List<Recommandation> getRecommandations() {
        return recommandations;
    }

    public void setRecommandations(List<Recommandation> recommandations) {
        this.recommandations = recommandations;
    }

 
    
}
