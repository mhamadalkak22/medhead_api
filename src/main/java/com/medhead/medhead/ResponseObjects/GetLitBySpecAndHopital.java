package com.medhead.medhead.ResponseObjects;

import java.util.List;

import com.medhead.medhead.entities.Lit;

public class GetLitBySpecAndHopital extends BaseResponse{

    private List<Lit> lits;

    public List<Lit> getLits() {
        return lits;
    }

    public void setLits(List<Lit> lits) {
        this.lits = lits;
    }
    
}
