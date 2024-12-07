package com.medhead.medhead.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Groupe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long groupeID;

    @Column(nullable = false, unique = true)
    private String name;

    public Groupe() {

    }

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
