package com.securityapp.spring_security.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.securityapp.spring_security.presentation.dto.UserDTO;
import com.securityapp.spring_security.service.impl.UserServiceImpl;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IUserService userService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    void registerUser_shouldReturnCreated() throws Exception {
        UserDTO user = new UserDTO(null, "juan", "password", "juan@example.com", Set.of("USER"));

        when(userService.save(user)).thenReturn(new UserDTO(1L, "juan", "password encripted", "juan@example.com,", Set.of("USER")));
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("juan"))
                .andExpect(jsonPath("$.password").value("password encripted"));
    }


    @Test
    void registerUser_shouldReturnBadRequest_whenUserIsInvalid() throws Exception {
        UserDTO user = new UserDTO(null, "", "password", "invalidemail", null);
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }
}
