package com.authentication.auth_application.controller;

import com.authentication.auth_application.dto.LoginRequest;
import com.authentication.auth_application.dto.UserRequest;
import com.authentication.auth_application.model.User;
import com.authentication.auth_application.service.AuthService;
import com.authentication.auth_application.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth/api")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserRequest userRequest) {
        User user = userService.createUser(userRequest);
        return ResponseEntity.ok("User created successfully!\nUser created with User Id :: " + user.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = authService.login(request);
            System.out.println("Token :: " + token);
            return ResponseEntity.ok(Map.of("Token", token));
        } catch (BadCredentialsException be) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("Error", "Invalid username or password."));
        } catch (UsernameNotFoundException ue) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("Error", "Username not found."));
        }
    }
}
