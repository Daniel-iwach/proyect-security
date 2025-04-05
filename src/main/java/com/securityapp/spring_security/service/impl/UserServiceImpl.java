package com.securityapp.spring_security.service.impl;

import com.securityapp.spring_security.persistence.model.RoleEntity;
import com.securityapp.spring_security.persistence.model.UserEntity;
import com.securityapp.spring_security.persistence.repository.UserRepository;
import com.securityapp.spring_security.presentation.dto.UserDTO;
import com.securityapp.spring_security.exception.UserNotFoundException;
import com.securityapp.spring_security.service.interfaces.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RolServiceImpl roleRolService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public UserDTO findByUsername(String username) {
        Optional<UserEntity>optional= userRepository.findUserEntityByUsername(username);
        if (optional.isPresent()){
            Set<String>setRoles=optional.get().getRoles().stream()
                    .map(rol->String.valueOf(rol.getName())).collect(Collectors.toSet());
            UserDTO userDTO = modelMapper.map(optional.get(),UserDTO.class);
            userDTO.setRoles(setRoles);
            return userDTO;
        }else{
            throw new UserNotFoundException(username);
        }
    }

    @Override
    public UserDTO save(UserDTO user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("that email is in use");
        }
        Set<RoleEntity> roles = roleRolService.findByNameIn(user.getRoles());
        UserEntity entity = modelMapper.map(user,UserEntity.class);
        entity.setId(null);
        entity.setRoles(roles);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return modelMapper.map(userRepository.save(entity),UserDTO.class);
    }


    @Override
    public UserDTO update(String username, UserDTO newUser) {
        Optional<UserEntity> optional = userRepository.findUserEntityByUsername(username);
        if (optional.isPresent()){
            UserEntity user = optional.get();
            if (!user.getEmail().equals(newUser.getEmail()) && userRepository.existsByEmail(newUser.getEmail())) {
                throw new IllegalArgumentException("that email is in use");
            }
            user.setUsername(newUser.getUsername());
            user.setEmail(newUser.getEmail());
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            Set<RoleEntity> roles = roleRolService.findByNameIn(newUser.getRoles());
            user.setRoles(roles);
            Set<String>setS=roles.stream()
                    .map(roleEntity ->String.valueOf(roleEntity.getName())).collect(Collectors.toSet());
            UserDTO userDTO=modelMapper.map(userRepository.save(user),UserDTO.class);
            userDTO.setRoles(setS);
            return userDTO;
        }else {
            throw new UserNotFoundException(username);
        }
    }

}