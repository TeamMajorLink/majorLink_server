package com.example.majorLink.repository;

import com.example.majorLink.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // 필요한 추가 쿼리 메소드가 있다면 여기에 정의
}
