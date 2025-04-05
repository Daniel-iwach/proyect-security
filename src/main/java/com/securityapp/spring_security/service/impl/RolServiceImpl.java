package com.securityapp.spring_security.service.impl;

import com.securityapp.spring_security.persistence.model.ERol;
import com.securityapp.spring_security.persistence.model.RoleEntity;
import com.securityapp.spring_security.persistence.repository.RoleRepository;
import com.securityapp.spring_security.service.interfaces.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class RolServiceImpl implements IRolService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void save(RoleEntity rol) {
        if (rol.getName()!=null) {
            roleRepository.save(rol);
        }else {
            throw new NullPointerException("the rol cannot be null");
        }
    }

    @Override
    public boolean existsByName(ERol rol) {
        return roleRepository.existsByName(rol);
    }

    @Override
    public Set<RoleEntity> findByNameIn(Set<String> names) {
        if (!names.isEmpty()){
            names = names.stream().map(String::toUpperCase).collect(Collectors.toSet());
            return roleRepository.findByNameIn(names);
        }else {
            throw new IllegalArgumentException("the set of strings cannot be empty");
        }
    }
}
