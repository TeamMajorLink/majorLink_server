package com.example.majorLink.repository;

import com.example.majorLink.domain.User;
import com.example.majorLink.domain.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.userStatus = 'ACTIVE' AND u.nickname = :nickname")
    Optional<User> findByNickname(String nickname);

    Optional<User> findByIdAndUserStatusIs(UUID uuid, UserStatus status);
}
