package com.medhead.medhead.RequestObjects;

import com.medhead.medhead.entities.Addresse;

public class SetHopitalAdresseRequest {

    private Addresse addresse;
    private long idHopital;
    public long getIdHopital() {
        return idHopital;
    }
    public void setIdHopital(long idHopital) {
        this.idHopital = idHopital;
    }
    public Addresse getAddresse() {
        return addresse;
    }
    public void setAddresse(Addresse addresse) {
        this.addresse = addresse;
    }
    

    
    
}
