package com.authentication.auth_application.controller;

import com.authentication.auth_application.dto.UserResponse;
import com.authentication.auth_application.dto.UserUpdateRequest;
import com.authentication.auth_application.model.User;
import com.authentication.auth_application.security.CustomUserDetails;
import com.authentication.auth_application.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<UserResponse> users = userService.getUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping("/request-update")
    public ResponseEntity<?> requestUpdate(@RequestBody UserUpdateRequest request) {
        userService.requestUpdate(request);
        return ResponseEntity.ok("Confirmation email sent.");
    }

    @GetMapping("/confirm-update")
    public ResponseEntity<String> confirmUpdate(@RequestParam(name = "token") String token) {
        System.out.println("UserController.confirmUpdate");
        userService.confirmUpdate(token);
        return ResponseEntity.ok("Details updated successfully.");
    }
}
