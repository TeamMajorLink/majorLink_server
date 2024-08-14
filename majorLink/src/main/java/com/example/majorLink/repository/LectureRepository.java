package com.example.majorLink.repository;

import com.example.majorLink.domain.Lecture;
import com.example.majorLink.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    @Query("SELECT lec FROM Lecture lec WHERE lec.user = :user AND lec.id = :lecId")
    Optional<Lecture> findByUser(User user, Long lecId);

}
