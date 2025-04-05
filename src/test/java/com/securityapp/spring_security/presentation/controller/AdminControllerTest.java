package com.securityapp.spring_security.presentation.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;
import java.util.Set;
import com.securityapp.spring_security.presentation.dto.UserDTO;
import com.securityapp.spring_security.exception.UserNotFoundException;
import com.securityapp.spring_security.service.interfaces.IAdminService;
import com.securityapp.spring_security.service.interfaces.IRolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IAdminService adminService;

    @Mock
    private IRolService rolService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    void findAllUsers_shouldReturnListOfUsers() throws Exception {

        List<UserDTO> users = List.of(
                new UserDTO(1L, "juan", "password", "juan@example.com", Set.of("ADMIN")),
                new UserDTO(2L, "pepe", "password", "pepe@example.com", Set.of("USER"))
        );

        when(adminService.findAll()).thenReturn(users);

        mockMvc.perform(get("/showAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Status 200 OK
                .andExpect(jsonPath("$.size()").value(users.size()))
                .andExpect(jsonPath("$[0].username").value("juan"))
                .andExpect(jsonPath("$[1].username").value("pepe"));

        verify(adminService, times(1)).findAll();
    }

    @Test
    void deleteUser_shouldReturnSuccessMessage() throws Exception {
        when(adminService.deleteByUsername("juan")).thenReturn("user juan has been deleted");
        mockMvc.perform(delete("/delete/{username}", "juan")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Status 200 OK
                .andExpect(content().string("user juan has been deleted"));

        verify(adminService, times(1)).deleteByUsername("juan");
    }

    @Test
    void deleteUser_shouldReturnNotFound() throws Exception {
        String username="juan";
        given(adminService.deleteByUsername(username))
                .willThrow(new UserNotFoundException(username));

        mockMvc.perform(delete("/delete/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}







