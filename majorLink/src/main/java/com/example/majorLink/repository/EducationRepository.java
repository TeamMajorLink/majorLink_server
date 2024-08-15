package com.example.majorLink.repository;

import com.example.majorLink.domain.Education;
import com.example.majorLink.domain.ProfileCard;
import com.example.majorLink.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface EducationRepository extends JpaRepository<Education, Long> {
    @Query("SELECT e FROM Education e WHERE e.user.userStatus = 'ACTIVE' AND e.user = :user")
    List<Education> findByUser(User user);
    @Query("SELECT e FROM Education e WHERE e.user.userStatus = 'ACTIVE' AND e.user.nickname = :nickname")
    List<Education> findByUserNickname(String nickname);
}
