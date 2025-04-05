package com.securityapp.spring_security.service.impl;

import com.securityapp.spring_security.persistence.repository.UserRepository;
import com.securityapp.spring_security.presentation.dto.UserDTO;
import com.securityapp.spring_security.exception.UserNotFoundException;
import com.securityapp.spring_security.service.interfaces.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> findAll() {
        List<UserDTO> users = userRepository.findAll().stream()
                .map(user -> UserDTO.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .email(user.getEmail())
                        .roles(user.getRoles().stream()
                                .map(role -> role.getName().name())
                                .collect(Collectors.toSet()))
                        .build())
                .toList();
        return users;
    }

    @Override
    public String deleteByUsername(String username) {
        if (userRepository.findUserEntityByUsername(username).isPresent()) {
            userRepository.deleteUserEntityByUsername(username);
            return "user "+username+" has been deleted";
        }else {
            throw new UserNotFoundException(username);
        }
    }
}
