package com.example.majorLink.repository;

import com.example.majorLink.domain.Lecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    @Query("SELECT l FROM Lecture l ORDER BY (SELECT COUNT(lk) FROM Liked lk WHERE lk.lecture.id = l.id) DESC")
    Page<Lecture> orderByLikedCount(Pageable pageable);

    @Query("SELECT l FROM Lecture l ORDER BY l.cNum DESC")
    Page<Lecture> orderByCurPNum(Pageable pageable);

    @Query("SELECT l FROM Lecture l WHERE l.category.id = :categoryId")
    Page<Lecture> findByCategoryId(Long categoryId, Pageable pageable);

    @Query("SELECT l FROM Lecture l ORDER BY l.createdAt DESC")
    Page<Lecture> orderByCreatedAt(Pageable pageable);

    @Query("SELECT l FROM Lecture l WHERE l.level = :level")
    Page<Lecture> findByLevel(int i, String level, Pageable pageable);
}