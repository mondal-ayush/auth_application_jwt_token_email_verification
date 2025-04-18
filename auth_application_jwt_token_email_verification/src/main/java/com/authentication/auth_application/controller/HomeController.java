package com.authentication.auth_application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home/api")
public class HomeController {

    @GetMapping
    public ResponseEntity<?> greetings() {
        return ResponseEntity.ok("Welcome to home page!");
    }

    @GetMapping("/me")
    public ResponseEntity<String> getCurrentUser(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok("Hello " + user.getUsername());
    }

}