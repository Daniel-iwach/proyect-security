package com.securityapp.spring_security.service.impl;


import com.securityapp.spring_security.persistence.model.ERol;
import com.securityapp.spring_security.persistence.model.RoleEntity;
import com.securityapp.spring_security.persistence.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RolServiceImplTest {
    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    RolServiceImpl rolService;

    @Test
    void save_shouldSaveRole_whenValidRoleProvided() {
        RoleEntity role = new RoleEntity(null, ERol.ADMIN);

        rolService.save(role);

        ArgumentCaptor<RoleEntity> captor = ArgumentCaptor.forClass(RoleEntity.class);
        verify(roleRepository, times(1)).save(captor.capture());

        RoleEntity savedRole = captor.getValue();
        assertNotNull(savedRole);
        assertEquals(ERol.ADMIN, savedRole.getName());
    }

    @Test
    void save_shouldThrowNullPointerException_whenRoleIsNull() {
        assertThrows(NullPointerException.class, () -> rolService.save(new RoleEntity()));
    }

    @Test
    void existsByName_shouldReturnFalse_whenRoleDoesNotExist() {
        when(roleRepository.existsByName(ERol.INVITED)).thenReturn(false);
        assertFalse(rolService.existsByName(ERol.INVITED));
    }

    @Test
    void findByNameIn_shouldReturnSetOfRoles_whenRolesExist() {
        RoleEntity role = new RoleEntity(null, ERol.ADMIN);
        Set<RoleEntity> set = Set.of(role);

        when(roleRepository.findByNameIn(Set.of("ADMIN"))).thenReturn(set);
        Set<RoleEntity> result = rolService.findByNameIn(Set.of("ADMIN"));

        assertEquals(set, result);
    }

    @Test
    void findByNameIn_shouldThrowIllegalArgumentException_whenSetIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> rolService.findByNameIn(Set.of()));
    }

}
