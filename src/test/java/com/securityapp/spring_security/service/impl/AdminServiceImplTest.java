package com.securityapp.spring_security.service.impl;

import com.securityapp.spring_security.persistence.model.ERol;
import com.securityapp.spring_security.persistence.model.RoleEntity;
import com.securityapp.spring_security.persistence.model.UserEntity;
import com.securityapp.spring_security.persistence.repository.UserRepository;
import com.securityapp.spring_security.presentation.dto.UserDTO;
import com.securityapp.spring_security.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RolServiceImpl rolService;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    AdminServiceImpl adminService;

    @Test
    void findAll_shouldReturnListOfUsers_whenCalled() {
        Set<RoleEntity> rol = Set.of(new RoleEntity(null, ERol.USER));
        List<UserEntity> list = List.of(
                new UserEntity(null, "Daniel", "1234", "daniel@gmail.com", rol),
                new UserEntity(null, "Juan", "1234", "juan@gmail.com", rol)
        );
        when(userRepository.findAll()).thenReturn(list);
        List<UserDTO> result = adminService.findAll();

        assertNotNull(result);
        assertEquals("Daniel", result.get(0).getUsername());
        assertEquals("Juan", result.get(1).getUsername());
    }

    @Test
    void deleteByUsername_shouldDeleteUser_whenUserExists() {
        String username = "testUser";

        when(userRepository.findUserEntityByUsername(any(String.class))).thenReturn(Optional.of(new UserEntity()));
        adminService.deleteByUsername(username);

        verify(userRepository, times(1)).deleteUserEntityByUsername(eq(username));
    }

    @Test
    void deleteByUsername_shouldThrowException_whenUserNotFound() {
        when(userRepository.findUserEntityByUsername(any(String.class))).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> adminService.deleteByUsername("pepe"));
    }
}

