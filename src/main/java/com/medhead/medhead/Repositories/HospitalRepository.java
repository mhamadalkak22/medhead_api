package com.medhead.medhead.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medhead.medhead.entities.Hopital;

@Repository
public interface HospitalRepository extends JpaRepository<Hopital, Long> {

    public Optional<Hopital> findByName(String name);
    public Optional<Hopital> findByUserId(long userID);
    
}
