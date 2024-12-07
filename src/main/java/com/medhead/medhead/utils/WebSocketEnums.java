package com.medhead.medhead.utils;

public enum WebSocketEnums {

    UPDATE_INVALID_COUNT("updateInvalidCount"),
    UPDATE_GROUPS("updateGroups"),
    UPDATE_LISTE_LITS("updateListeList"),
    UPDATE_HOSPITAL_SPEC_LIST("updateHospitalSpecList");
 
    private final String webSocketPath;



    public String getWebSocketPath() {
        return webSocketPath;
    }



    private WebSocketEnums(String webSocketPath)
    {
        this.webSocketPath = webSocketPath;
    }

    
    
}
