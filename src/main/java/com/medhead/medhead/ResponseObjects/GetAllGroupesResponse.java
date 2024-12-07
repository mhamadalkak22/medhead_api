package com.medhead.medhead.ResponseObjects;

import java.util.List;

import com.medhead.medhead.entities.Groupe;

public class GetAllGroupesResponse extends BaseResponse{

    private List<Groupe> groupes;

    public List<Groupe> getGroupes() {
        return groupes;
    }

    public void setGroupes(List<Groupe> groupes) {
        this.groupes = groupes;
    }
    
}
