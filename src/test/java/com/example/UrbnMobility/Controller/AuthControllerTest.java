package com.example.UrbnMobility.Controller;

import com.example.UrbnMobility.Database.Database;
import com.example.UrbnMobility.config.SecurityConfig;
import com.example.UrbnMobility.model.AuthDetail;
import com.example.UrbnMobility.security.JwtCreater;
import com.example.UrbnMobility.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
@SpringJUnitWebConfig
@WebMvcTest(AuthController.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtCreater jwtCreater;

    @Mock
    private Database db;

    @Mock
    private SecurityConfig config;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @Test
    public void testAuthenticate() throws Exception {
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername("testUser")
                .password("testPassword")
                .authorities(Collections.emptyList())
                .build();

        String jwtToken = "mockedJwtToken";

        Mockito.when(authenticationManager.authenticate(Mockito.any()))
                .thenReturn(null);

        Mockito.when(userService.loadUserByUsername(Mockito.anyString()))
                .thenReturn(userDetails);

        Mockito.when(jwtCreater.generateToken(userDetails))
                .thenReturn(jwtToken);

        AuthDetail authDetail = new AuthDetail();
        authDetail.setUsername("testUser");
        authDetail.setPassword("testPassword");

        mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                        .content(asJsonString(authDetail))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("Invalid credentials"));

    }

    @Test
    public void testAuthenticateWithInvalidCredentials() throws Exception {
        Mockito.when(authenticationManager.authenticate(Mockito.any()))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        AuthDetail authDetail = new AuthDetail();
        authDetail.setUsername("invalidUser");
        authDetail.setPassword("invalidPassword");

        mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                        .content(asJsonString(authDetail))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("Invalid credentials"));
    }

    // Helper method to convert an object to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}