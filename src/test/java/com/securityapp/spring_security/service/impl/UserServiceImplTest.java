package com.securityapp.spring_security.service.impl;

import com.securityapp.spring_security.persistence.model.ERol;
import com.securityapp.spring_security.persistence.model.RoleEntity;
import com.securityapp.spring_security.persistence.model.UserEntity;
import com.securityapp.spring_security.persistence.repository.UserRepository;
import com.securityapp.spring_security.presentation.dto.UserDTO;
import com.securityapp.spring_security.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RolServiceImpl rolService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void findByUsername_shouldReturnUser_whenUserExists() {
        Set<RoleEntity> rol = Set.of(new RoleEntity(null, ERol.USER));
        Optional<UserEntity> optional = Optional.of(
                new UserEntity(1L,"Daniel", "1234", "daniel@gmail.com", rol)
        );
        when(userRepository.findUserEntityByUsername(any(String.class))).thenReturn(optional);
        when(modelMapper.map(optional.get(), UserDTO.class)).thenReturn(
                new UserDTO(1L,"Daniel", "1234", "daniel@gmail.com", null));
        UserDTO result = userService.findByUsername("Daniel");

        assertNotNull(result);
        assertEquals("Daniel", result.getUsername());
        assertEquals("daniel@gmail.com", result.getEmail());
    }

    @Test
    void findByUsername_shouldReturnEmptyOptional_whenUserDoesNotExist() {
        when(userRepository.findUserEntityByUsername(any(String.class))).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,()->userService.findByUsername("userNotExisting"));
    }

    @Test
    void testSave() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setEmail("testuser@example.com");
        userDTO.setPassword("1234");
        userDTO.setRoles(Set.of("ADMIN"));

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(ERol.ADMIN);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setEmail("testuser@example.com");
        userEntity.setPassword("1234");

        UserEntity savedEntity = new UserEntity();
        savedEntity.setUsername("testuser");
        savedEntity.setEmail("testuser@example.com");
        savedEntity.setPassword("hashed_password");
        savedEntity.setRoles(Set.of(roleEntity));

        UserDTO expectedUserDTO = new UserDTO();
        expectedUserDTO.setUsername("testuser");
        expectedUserDTO.setEmail("testuser@example.com");
        expectedUserDTO.setRoles(Set.of("ADMIN"));

        when(rolService.findByNameIn(userDTO.getRoles())).thenReturn(Set.of(roleEntity));
        when(modelMapper.map(userDTO, UserEntity.class)).thenReturn(userEntity);
        when(passwordEncoder.encode("1234")).thenReturn("hashed_password");
        when(userRepository.save(userEntity)).thenReturn(savedEntity);
        when(modelMapper.map(savedEntity, UserDTO.class)).thenReturn(expectedUserDTO);

        UserDTO result = userService.save(userDTO);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("testuser@example.com", result.getEmail());
        assertEquals(Set.of("ADMIN"), result.getRoles());
        assertEquals("hashed_password", userEntity.getPassword());

        verify(rolService).findByNameIn(userDTO.getRoles());
        verify(modelMapper).map(userDTO, UserEntity.class);
        verify(passwordEncoder).encode("1234");
        verify(userRepository).save(userEntity);
        verify(modelMapper).map(savedEntity, UserDTO.class);


    }

    @Test
    void testUpdate_Success() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(ERol.ADMIN);

        UserEntity savedEntity = new UserEntity();
        savedEntity.setUsername("testuser");
        savedEntity.setEmail("testuser@example.com");
        savedEntity.setPassword("hashed_password");
        savedEntity.setRoles(Set.of(roleEntity));

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setEmail("testuser@example.com");
        userDTO.setPassword("1234");
        userDTO.setRoles(Set.of("ADMIN"));

        when(userRepository.findUserEntityByUsername("testuser")).thenReturn(Optional.of(savedEntity));
        when(passwordEncoder.encode("1234")).thenReturn("hashed_password");
        when(rolService.findByNameIn(userDTO.getRoles())).thenReturn(savedEntity.getRoles());
        when(userRepository.save(savedEntity)).thenReturn(savedEntity);

        UserDTO expectedUserDTO = new UserDTO();
        expectedUserDTO.setUsername("testuser");
        expectedUserDTO.setEmail("testuser@example.com");
        expectedUserDTO.setPassword("hashed_password");
        expectedUserDTO.setRoles(Set.of("ADMIN"));

        when(modelMapper.map(savedEntity, UserDTO.class)).thenReturn(expectedUserDTO);

        UserDTO result = userService.update("testuser", userDTO);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("testuser@example.com", result.getEmail());
        assertEquals("hashed_password", result.getPassword());
        assertEquals(1, result.getRoles().size());
    }




    @Test
    void update_shouldThrowUserNotFoundException_whenUserDoesNotExist() {
        when(userRepository.findUserEntityByUsername(any(String.class))).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.update("juan", new UserDTO()));
    }

}
