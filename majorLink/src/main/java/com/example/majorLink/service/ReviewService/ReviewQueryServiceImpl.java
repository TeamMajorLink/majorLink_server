package com.example.majorLink.service.ReviewService;

import com.example.majorLink.domain.Lecture;
import com.example.majorLink.domain.Review;
import com.example.majorLink.repository.LectureRepository;
import com.example.majorLink.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryServiceImpl implements ReviewQueryService{

    private final ReviewRepository reviewRepository;
    private final LectureRepository lectureRepository;

    // 리뷰 페이징
    @Override
    public Page<Review> getReviewList(Long lectureId, Integer page) {
        Lecture lecture = lectureRepository.findById(lectureId).get();

        Page<Review> reviewPage = reviewRepository.findAllByLecture(lecture, PageRequest.of(page, 10));

        return reviewPage;
    }
}
