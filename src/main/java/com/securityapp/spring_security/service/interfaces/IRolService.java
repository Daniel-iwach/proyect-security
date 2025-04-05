package com.securityapp.spring_security.service.interfaces;

import com.securityapp.spring_security.persistence.model.ERol;
import com.securityapp.spring_security.persistence.model.RoleEntity;

import java.util.Set;

public interface IRolService {
    void save(RoleEntity rol);
    boolean existsByName(ERol rol);
    Set<RoleEntity> findByNameIn(Set<String> names);
}
