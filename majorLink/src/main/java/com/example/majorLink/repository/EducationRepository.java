package com.example.majorLink.repository;

import com.example.majorLink.domain.Education;
import com.example.majorLink.domain.ProfileCard;
import com.example.majorLink.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {
    List<Education> findByUser(User user);
}