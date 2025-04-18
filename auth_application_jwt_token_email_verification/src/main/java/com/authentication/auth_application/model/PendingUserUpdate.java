package com.authentication.auth_application.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PendingUserUpdate {

    @Id
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String username;
    private String email;
    private String role;
    private Boolean enabled;

    private LocalDateTime expiry;

    private boolean confirmed = false;

    private LocalDateTime createdAt = LocalDateTime.now();
}
