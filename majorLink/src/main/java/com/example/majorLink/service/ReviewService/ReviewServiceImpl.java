package com.example.majorLink.service.ReviewService;

import com.example.majorLink.domain.Review;
import com.example.majorLink.dto.request.ReviewRequestDTO;
import com.example.majorLink.repository.LectureRepository;
import com.example.majorLink.repository.ReviewRepository;
import com.example.majorLink.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;

    // 리뷰 생성
    @Override
    public Review createReview(Long userId, Long lectureId, ReviewRequestDTO request) {
        Review review = Review.builder()
                .title(request.getTitle())
                .rate(request.getRate())
                .content(request.getContent())
                .build();

        review.setUser(userRepository.findById(userId).get());
        review.setLecture(lectureRepository.findById(lectureId).get());

        return reviewRepository.save(review);
    }

    // 리뷰 수정
    @Override
    public Review updateReview(Long reviewId, ReviewRequestDTO request) {
        Review review = reviewRepository.findById(reviewId).get();

        review.updateReview(request.getTitle(), request.getContent(), request.getRate());

        return reviewRepository.save(review);
    }

    // 리뷰 삭제
    @Override
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).get();

        reviewRepository.delete(review);
    }
}
