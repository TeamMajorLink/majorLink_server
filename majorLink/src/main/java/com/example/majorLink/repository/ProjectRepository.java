package com.example.majorLink.repository;

import com.example.majorLink.domain.Project;
import com.example.majorLink.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p WHERE p.user.userStatus = 'ACTIVE' AND p.user = :user")
    List<Project> findByUser(User user);
    @Query("SELECT p FROM Project p WHERE p.user.userStatus = 'ACTIVE' AND p.user.nickname = :nickname")
    List<Project> findByUserNickname(String nickname);
}
