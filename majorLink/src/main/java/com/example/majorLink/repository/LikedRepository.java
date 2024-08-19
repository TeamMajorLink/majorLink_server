package com.example.majorLink.repository;

import com.example.majorLink.domain.Lecture;
import com.example.majorLink.domain.User;
import com.example.majorLink.domain.mapping.Liked;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedRepository extends JpaRepository<Liked, Long> {
    Boolean existsByUserAndLecture(User user, Lecture lecture);

    void deleteByUserAndLecture(User user, Lecture lecture);

    void deleteAllByLecture(Lecture lecture);
}