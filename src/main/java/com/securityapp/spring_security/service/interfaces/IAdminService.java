package com.securityapp.spring_security.service.interfaces;

import com.securityapp.spring_security.presentation.dto.UserDTO;

import java.util.List;

public interface IAdminService {
    List<UserDTO> findAll();
    String deleteByUsername(String username);
}
