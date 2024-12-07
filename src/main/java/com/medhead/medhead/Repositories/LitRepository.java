package com.medhead.medhead.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medhead.medhead.entities.Lit;

@Repository
public interface LitRepository extends JpaRepository<Lit, Long> {

    public List<Lit> findByHopitalIdAndSpecialiteId(long hopitalRepo, long specID);

    public List<Lit> findBySpecialiteId(long specID);

}
