package com.example.majorLink.repository;

import com.example.majorLink.domain.ProfileCard;
import com.example.majorLink.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileCardRepository extends JpaRepository<ProfileCard, Long> {
    Optional<ProfileCard> findByUser(User user);
}