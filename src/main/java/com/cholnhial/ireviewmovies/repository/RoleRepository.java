package com.cholnhial.ireviewmovies.repository;

import com.cholnhial.ireviewmovies.model.Role;
import com.cholnhial.ireviewmovies.model.enumeration.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RoleType role);
}
