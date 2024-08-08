package com.example.majorLink.service.ReviewService;

import com.example.majorLink.domain.Review;
import org.springframework.data.domain.Page;

public interface ReviewQueryService {

    Page<Review> getReviewList(Long lectureId, Integer page);
}
