package com.example.majorLink.repository;

import com.example.majorLink.domain.User;
import com.example.majorLink.domain.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
