package com.medhead.medhead.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Reservation {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reservationID;

    @OneToOne(targetEntity = Hopital.class)
    @JoinColumn(name = "hopital", nullable = false)
    private Hopital hopital;

    @OneToOne(targetEntity = Specialite.class)
    @JoinColumn(name = "specialite", nullable = false)
    private Specialite spec;

    @OneToOne(targetEntity = Lit.class)
    @JoinColumn(name = "lit", nullable = false)
    private Lit lit;

    public Lit getLit() {
        return lit;
    }

    public void setLit(Lit lit) {
        this.lit = lit;
    }

    @Column(name = "patientName", nullable = false)
    private String name;

    public long getReservationID() {
        return reservationID;
    }

    public void setReservationID(long reservationID) {
        this.reservationID = reservationID;
    }

    public Hopital getHopital() {
        return hopital;
    }

    public void setHopital(Hopital hopital) {
        this.hopital = hopital;
    }

    public Specialite getSpec() {
        return spec;
    }

    public void setSpec(Specialite spec) {
        this.spec = spec;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


 
    

    
}
