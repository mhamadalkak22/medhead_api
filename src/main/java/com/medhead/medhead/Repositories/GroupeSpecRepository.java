package com.medhead.medhead.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medhead.medhead.entities.Groupe;

@Repository
public interface GroupeSpecRepository extends JpaRepository<Groupe, Long> {

}
