package com.securityapp.spring_security.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.securityapp.spring_security.presentation.dto.UserDTO;
import com.securityapp.spring_security.exception.UserNotFoundException;
import com.securityapp.spring_security.service.interfaces.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Set;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IUserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }


    @Test
    void getUserProfile_shouldReturnUser_whenUserExists() throws Exception {
        String username = "juan";
        UserDTO user = new UserDTO(null, "juan", "password", "juan@example.com", Set.of("USER"));

        when(userService.findByUsername(username)).thenReturn(user);

        mockMvc.perform(get("/get/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("juan"))
                .andExpect(jsonPath("$.email").value("juan@example.com"));
    }

    @Test
    void getUserProfile_shouldReturnNotFound_whenUserDoesNotExist() throws Exception {
        String username = "juan";

        given(userService.findByUsername(username))
                .willThrow(new UserNotFoundException(username));

        mockMvc.perform(get("/users/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_shouldReturnOk_whenUserExists() throws Exception {
        String username = "juan";
        UserDTO updatedUser = new UserDTO(null, "juan", "newpassword", "juan_updated@example.com", Set.of("USER"));
        UserDTO returnUserUpdate = new UserDTO(1L, "juan", "newpassword encripted", "juan_updated@example.com", Set.of("USER"));

        when(userService.update(username,updatedUser)).thenReturn(returnUserUpdate);

        mockMvc.perform(put("/update/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").value("newpassword encripted"));
    }

    @Test
    void updateUser_shouldReturnNotFound_whenUserDoesNotExist() throws Exception {
        String username = "juan";
        UserDTO updatedUser = new UserDTO(null, "juan", "newpassword", "juan_updated@example.com", Set.of("USER"));

        given(userService.update(username,updatedUser))
                .willThrow(new UserNotFoundException(username));

        mockMvc.perform(put("/update/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isNotFound());

    }
}
