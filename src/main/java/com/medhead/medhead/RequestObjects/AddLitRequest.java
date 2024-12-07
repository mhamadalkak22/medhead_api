package com.medhead.medhead.RequestObjects;

public class AddLitRequest {

    private long hopitalID;
    private long specialiteID;
    private int nombre;
    private boolean libre;
    
    public boolean isLibre() {
        return libre;
    }
    public void setLibre(boolean libre) {
        this.libre = libre;
    }
    public int getNombre() {
        return nombre;
    }
    public void setNombre(int nombre) {
        this.nombre = nombre;
    }
    public long getHopitalID() {
        return hopitalID;
    }
    public void setHopitalID(long hopitalID) {
        this.hopitalID = hopitalID;
    }
    public long getSpecialiteID() {
        return specialiteID;
    }
    public void setSpecialiteID(long specialiteID) {
        this.specialiteID = specialiteID;
    }

    
    
}
