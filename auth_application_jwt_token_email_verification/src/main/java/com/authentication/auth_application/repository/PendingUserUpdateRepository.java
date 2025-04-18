package com.authentication.auth_application.repository;

import com.authentication.auth_application.model.PendingUserUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingUserUpdateRepository extends JpaRepository<PendingUserUpdate, String> {
}
