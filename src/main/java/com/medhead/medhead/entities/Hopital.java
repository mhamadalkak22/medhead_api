package com.medhead.medhead.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import java.util.List;

@Entity(name = "hopital")
public class Hopital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(targetEntity = AppUser.class)
    @JoinColumn(name = "userID", nullable = false)
    private AppUser user;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToOne(targetEntity =  Addresse.class)
    @JoinColumn(name ="addresseID", nullable= true )
    private Addresse addresse;

    public Addresse getAddresse() {
        return addresse;
    }

    public void setAddresse(Addresse addresse) {
        this.addresse = addresse;
    }

    @ManyToMany
    @JoinColumn(name = "id")
    private List<Specialite> specialites;

    public List<Specialite> getSpecialites() {
        return specialites;
    }

    public void setSpecialites(List<Specialite> specialites) {
        this.specialites = specialites;
    }

    public Hopital() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nom) {
        this.name = nom;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }


  

}
