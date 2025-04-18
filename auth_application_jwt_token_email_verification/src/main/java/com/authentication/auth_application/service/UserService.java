package com.authentication.auth_application.service;

import com.authentication.auth_application.dto.UserRequest;
import com.authentication.auth_application.dto.UserResponse;
import com.authentication.auth_application.dto.UserUpdateRequest;
import com.authentication.auth_application.model.PendingUserUpdate;
import com.authentication.auth_application.model.User;
import com.authentication.auth_application.repository.PendingUserUpdateRepository;
import com.authentication.auth_application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PendingUserUpdateRepository pendingUserUpdateRepository;
    private final PasswordEncoder encoder;
    private final EmailService emailService;

    @Value("${user.update.confirmation.link}")
    private String confirmationLink;

    public UserService(UserRepository userRepository, PendingUserUpdateRepository pendingUserUpdateRepository, PasswordEncoder encoder, EmailService emailService) {

        this.userRepository = userRepository;
        this.pendingUserUpdateRepository = pendingUserUpdateRepository;
        this.encoder = encoder;
        this.emailService = emailService;
    }

    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();

        List<UserResponse> userDtos = users.stream().map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getEnabled())).toList();

        userDtos.forEach(System.out::println);
        return userDtos;
    }

    public User createUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(encoder.encode(userRequest.getPassword()));
        userRepository.save(user);
        return user;
    }

    public void requestUpdate(UserUpdateRequest request) {

        User user = userRepository.findById(request.getId()).orElseThrow(() -> new UsernameNotFoundException("User not found."));

        String token = UUID.randomUUID().toString();

        PendingUserUpdate pending = new PendingUserUpdate();

        pending.setToken(token);
        pending.setUser(user);
        pending.setUsername(request.getUsername());
        pending.setEmail(request.getEmail());
        pending.setRole(request.getRole());
        pending.setEnabled(request.getEnabled());

        pending.setExpiry(LocalDateTime.now().plusMinutes(5));

        pendingUserUpdateRepository.save(pending);

        String mailSubject = "TEST | Confirmation: Updated account details";
        String mailBody = String.format(
                "<p>Hi %s,</p>" +
                        "<p>Click the link below to confirm your updated account details:</p>" +
                        "<p><a href=\"%s\" style=\"color: #1a73e8; text-decoration: none;\">Confirm Account Details</a></p>" +
                        "<br>" +
                        "<p>Regards,<br>SeeUs Team</p>",
                user.getUsername(),
                confirmationLink + token
        );


        emailService.sendEmail(user.getEmail(), mailSubject,mailBody);
    }

    public void confirmUpdate(String token) {
        System.out.println("UserService.confirmUpdate");
        PendingUserUpdate pending = pendingUserUpdateRepository.findById(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        if (LocalDateTime.now().isAfter(pending.getExpiry())) {
            throw new RuntimeException("Token expired");
        }

        User user = pending.getUser();
        user.setUsername(pending.getUsername());
        user.setEmail(pending.getEmail());
        user.setRole(pending.getRole());
        user.setEnabled(pending.getEnabled());

        userRepository.save(user);

        pending.setConfirmed(true);
        pendingUserUpdateRepository.save(pending);
    }

}
