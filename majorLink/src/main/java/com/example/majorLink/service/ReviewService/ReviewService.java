package com.example.majorLink.service.ReviewService;

import com.example.majorLink.domain.Review;
import com.example.majorLink.domain.User;
import com.example.majorLink.dto.request.ReviewRequestDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ReviewService {

    Review createReview(UUID user, Long lectureId, ReviewRequestDTO request);
    Review updateReview(Long reviewId, ReviewRequestDTO request);
    void deleteReview(Long reviewId);

    Page<Review> getReviewList(Long lectureId, Integer page);
}
