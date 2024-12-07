package com.medhead.medhead.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medhead.medhead.entities.Specialite;


@Repository
public interface SpecialiteRepository extends JpaRepository<Specialite, Long> {

    public List<Specialite> findByGroupeGroupeID(long groupeID);

    public List<Specialite> findByName(String name);

}