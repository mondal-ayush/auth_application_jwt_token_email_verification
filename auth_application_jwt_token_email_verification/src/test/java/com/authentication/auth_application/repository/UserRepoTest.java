package com.authentication.auth_application.repository;

import com.authentication.auth_application.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserRepoTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Test
    public void createUser() {
        User user1 = new User();
        user1.setUsername("johndoe31");
        user1.setEmail("johndoe31@example.com");
        user1.setPassword(encoder.encode("password123"));
        user1.setRole("ROLE_USER");
        user1.setEnabled(true);

        User user2 = new User();
        user2.setUsername("janedoe99");
        user2.setEmail("janedoe99@example.com");
        user2.setPassword(encoder.encode("password456"));
        user2.setRole("ROLE_ADMIN");
        user2.setEnabled(false);

        userRepository.save(user1);
        userRepository.save(user2);
    }
}
