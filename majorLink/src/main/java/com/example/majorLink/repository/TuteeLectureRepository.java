package com.example.majorLink.repository;

import com.example.majorLink.domain.mapping.TuteeLecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TuteeLectureRepository extends JpaRepository<TuteeLecture, Long> {
    Optional<TuteeLecture> findByUserIdAndLectureId(UUID userId, Long lectureId);
}