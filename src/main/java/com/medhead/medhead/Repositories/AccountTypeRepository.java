package com.medhead.medhead.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medhead.medhead.entities.AccountType;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {

}
