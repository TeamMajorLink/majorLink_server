package com.example.majorLink.repository;

import com.example.majorLink.domain.ProfileCard;
import com.example.majorLink.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ProfileCardRepository extends JpaRepository<ProfileCard, Long> {
    @Query("SELECT pc FROM ProfileCard pc WHERE pc.user.userStatus = 'ACTIVE' AND pc.user = :user")
    Optional<ProfileCard> findByUser(User user);
    @Query("SELECT pc FROM ProfileCard pc WHERE pc.user.userStatus = 'ACTIVE' AND pc.user.nickname = :nickname")
    Optional<ProfileCard> findByUserNickname(String nickname);
}
