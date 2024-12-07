package com.medhead.medhead.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Lit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long litId;

    @OneToOne(targetEntity = Hopital.class)
    @JoinColumn(name = "hopitalId", nullable = true)
    private Hopital hopital;

    @OneToOne(targetEntity = Specialite.class)
    @JoinColumn(name = "specID", nullable = true)
    private Specialite specialite;

    @Column(name = "libre", nullable = false)
    private boolean libre;

    @Column(name = "reserved", nullable = false, columnDefinition = "boolean default false")
    private boolean reserved;

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public long getLitId() {
        return litId;
    }

    public void setLitId(long litId) {
        this.litId = litId;
    }

    public Hopital getHopital() {
        return hopital;
    }

    public void setHopital(Hopital hopital) {
        this.hopital = hopital;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public boolean isLibre() {
        return libre;
    }

    public void setLibre(boolean libre) {
        this.libre = libre;
    }

}
