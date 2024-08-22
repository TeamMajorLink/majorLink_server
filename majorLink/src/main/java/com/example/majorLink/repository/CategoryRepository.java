package com.example.majorLink.repository;

import com.example.majorLink.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT DISTINCT c.mainCategory FROM Category c")
    List<String> findMainCategory();

    Optional<Category> findByMainCategory(String mainCategory);
}
