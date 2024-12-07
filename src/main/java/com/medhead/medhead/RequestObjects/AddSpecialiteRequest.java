package com.medhead.medhead.RequestObjects;

public class AddSpecialiteRequest {

    private String name;
    private long groupeID;

   

    public long getGroupeID() {
        return groupeID;
    }

    public void setGroupeID(long groupeID) {
        this.groupeID = groupeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    
}
