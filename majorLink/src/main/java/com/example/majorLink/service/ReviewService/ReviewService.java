package com.example.majorLink.service.ReviewService;

import com.example.majorLink.domain.Review;
import com.example.majorLink.dto.request.ReviewRequestDTO;
import org.springframework.data.domain.Page;

public interface ReviewService {

    Review createReview(Long userId, Long lectureId, ReviewRequestDTO request);
    Review updateReview(Long reviewId, ReviewRequestDTO request);
    void deleteReview(Long reviewId);

    Page<Review> getReviewList(Long lectureId, Integer page);
}
