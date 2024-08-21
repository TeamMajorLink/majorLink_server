package com.example.majorLink.service.ReviewService;

import com.example.majorLink.domain.Lecture;
import com.example.majorLink.domain.Review;
import com.example.majorLink.domain.User;
import com.example.majorLink.dto.request.ReviewRequestDTO;
import com.example.majorLink.repository.LectureRepository;
import com.example.majorLink.repository.ReviewRepository;
import com.example.majorLink.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;

    // 리뷰 생성
    @Override
    public Review createReview(UUID userId, Long lectureId, ReviewRequestDTO request) {
        Review review = Review.builder()
                .rate(request.getRate())
                .content(request.getContent())
                .build();

        review.setUser(userRepository.findById(userId).get());
        review.setLecture(lectureRepository.findById(lectureId).get());

        return reviewRepository.save(review);
    }

    // 리뷰 수정
    @Override
    public Review updateReview(UUID userId, Long reviewId, ReviewRequestDTO request) {
        User user = userRepository.findById(userId).get();
        Review review = reviewRepository.findById(reviewId).get();

        review.updateReview(request.getContent(), request.getRate());

        if (!review.getUser().getId().equals(user.getId())){
            throw new IllegalArgumentException("Invalid user ID");
        }

        return reviewRepository.save(review);
    }

    // 리뷰 삭제
    @Override
    public void deleteReview(UUID userId, Long reviewId) {
        User user = userRepository.findById(userId).get();
        Review review = reviewRepository.findById(reviewId).get();

        if (!review.getUser().getId().equals(user.getId())){
            throw new IllegalArgumentException("Invalid user ID");
        }

        reviewRepository.delete(review);
    }

    // 리뷰 페이징
    @Override
    public Page<Review> getReviewList(Long lectureId, Integer page) {
        Lecture lecture = lectureRepository.findById(lectureId).get();

        Page<Review> reviewPage = reviewRepository.findAllByLecture(lecture, PageRequest.of(page, 10));

        return reviewPage;
    }

    // 리뷰 내용 조회
    @Override
    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId).get();
    }
}
