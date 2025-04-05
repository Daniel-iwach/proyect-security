package com.securityapp.spring_security.persistence.repository;

import com.securityapp.spring_security.persistence.model.ERol;
import com.securityapp.spring_security.persistence.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
    boolean existsByName(ERol rol);
    Set<RoleEntity> findByNameIn(Set<String> names);
}
