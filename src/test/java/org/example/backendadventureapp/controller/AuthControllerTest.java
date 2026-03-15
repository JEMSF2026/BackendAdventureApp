package org.example.backendadventureapp.controller;

import org.example.backendadventureapp.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Test
    void login_returns200WhenCredentialsAreValid() throws Exception {
        when(authService.login("jens@jemsadventure.dk", "Hansen5678")).thenReturn(true);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"jens@jemsadventure.dk\",\"password\":\"Hansen5678\"}"))
                .andExpect(status().isOk());

        verify(authService, times(1)).login("jens@jemsadventure.dk", "Hansen5678");
    }

    @Test
    void login_returns401WhenCredentialsAreInvalid() throws Exception {
        when(authService.login("jens@jemsadventure.dk", "WrongPassword")).thenReturn(false);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"jens@jemsadventure.dk\",\"password\":\"WrongPassword\"}"))
                .andExpect(status().isUnauthorized());

        verify(authService, times(1)).login("jens@jemsadventure.dk", "WrongPassword");
    }
}