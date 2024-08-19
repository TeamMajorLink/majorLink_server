package com.example.majorLink.repository;

import com.example.majorLink.domain.Lecture;
import com.example.majorLink.domain.mapping.TutorLecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TutorLectureRepository extends JpaRepository<TutorLecture, Long> {
    Optional<TutorLecture> findByLectureId(Long id);

    void deleteAllByLecture(Lecture lecture);
}
