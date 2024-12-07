package com.medhead.medhead.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medhead.medhead.entities.AppAuthorization;

@Repository
public interface AuthorizationRepository extends JpaRepository<AppAuthorization, Long> {

}
