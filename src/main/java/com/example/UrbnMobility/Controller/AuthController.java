package com.example.UrbnMobility.Controller;

import com.example.UrbnMobility.model.AuthDetail;
import com.example.UrbnMobility.security.JwtCreater;
import com.example.UrbnMobility.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtCreater jwtCreater;
    private final UserService userService;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            JwtCreater jwtCreater,
            UserService userService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtCreater = jwtCreater;
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthDetail authDetail) {
        String username = authDetail.getUsername();
        String password = authDetail.getPassword();
        // Authenticate the user
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

        // Set the authentication in the SecurityContext
         SecurityContextHolder.getContext().setAuthentication(authentication);

        // Load user details
        UserDetails userDetails = userService.loadUserByUsername(username);

        // Generate JWT token
        String jwt = jwtCreater.generateToken(userDetails);

        // Return the JWT token in the response
        return ResponseEntity.ok(jwt);
    }
}
