package com.example.majorLink.repository;

import com.example.majorLink.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByUserId(UUID userId);
}
