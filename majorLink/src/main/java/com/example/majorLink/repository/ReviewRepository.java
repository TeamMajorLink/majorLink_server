package com.example.majorLink.repository;

import com.example.majorLink.domain.Lecture;
import com.example.majorLink.domain.Review;
import com.example.majorLink.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByUser(User user, PageRequest pageRequest);

    @Query("SELECT AVG(r.rate) FROM Review r WHERE r.lecture.id = :lectureId")
    Double findAverageRatingByLectureId(Long lectureId);
}
