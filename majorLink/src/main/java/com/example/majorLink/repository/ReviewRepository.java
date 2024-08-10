package com.example.majorLink.repository;

import com.example.majorLink.domain.Lecture;
import com.example.majorLink.domain.Review;
import com.example.majorLink.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByLecture(Lecture lecture, PageRequest pageRequest);
    Page<Review> findAllByUser(User user, PageRequest pageRequest);
}
