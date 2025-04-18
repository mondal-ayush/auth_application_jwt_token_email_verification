package com.authentication.auth_application.service;

import com.authentication.auth_application.dto.LoginRequest;
import com.authentication.auth_application.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    public String login(LoginRequest request) {

        System.out.println(request.getUsernameOrEmail() + " : " + request.getPassword());
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsernameOrEmail());
        return jwtUtil.generateToken(userDetails.getUsername());
    }
}
