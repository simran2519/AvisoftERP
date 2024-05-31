package com.ERP.repositories;

import com.ERP.entities.Authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<Authentication,Long> {

    public Authentication findByUsername(String username);
}
