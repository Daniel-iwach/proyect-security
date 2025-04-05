package com.securityapp.spring_security.service.interfaces;

import com.securityapp.spring_security.persistence.model.RoleEntity;
import com.securityapp.spring_security.persistence.model.UserEntity;
import com.securityapp.spring_security.presentation.dto.UserDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IUserService {

    UserDTO findByUsername(String username);

    UserDTO save(UserDTO user);

    UserDTO update(String username,UserDTO newUser);
}
