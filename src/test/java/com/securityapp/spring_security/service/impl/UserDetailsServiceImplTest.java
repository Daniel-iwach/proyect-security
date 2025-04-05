package com.securityapp.spring_security.service.impl;

import com.securityapp.spring_security.persistence.model.ERol;
import com.securityapp.spring_security.persistence.model.RoleEntity;
import com.securityapp.spring_security.persistence.model.UserEntity;
import com.securityapp.spring_security.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsername_shouldReturnUserDetails_whenUserExists() {
        Set<RoleEntity> roles = Set.of(new RoleEntity(1L, ERol.USER));
        UserEntity userEntity = new UserEntity(1L, "testUser", "password", "test@example.com", roles);

        when(userRepository.findUserEntityByUsername("testUser"))
                .thenReturn(Optional.of(userEntity));

        UserDetails result = userDetailsService.loadUserByUsername("testUser");

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        assertEquals("password", result.getPassword());
        assertTrue(result.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void loadUserByUsername_shouldThrowUsernameNotFoundException_whenUserDoesNotExist() {
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("user false"));
    }

}
