package com.example.majorLink.controller;

import com.example.majorLink.domain.Review;
import com.example.majorLink.domain.User;
import com.example.majorLink.dto.request.ReviewRequestDTO;
import com.example.majorLink.dto.response.ReviewResponseDTO;
import com.example.majorLink.global.auth.AuthUser;
import com.example.majorLink.service.ReviewService.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 추가하는 api
    @PostMapping("/{lectureId}")
    @ResponseBody
    public ReviewResponseDTO.CreateReview createReview(@RequestBody ReviewRequestDTO request,
                                                       @PathVariable(name = "lectureId") Long lectureId,
                                                       @AuthenticationPrincipal AuthUser authUser){
        User user = authUser.getUser();
        Review review = reviewService.createReview(user.getId(), lectureId, request);

        return ReviewResponseDTO.CreateReview.builder()
                .reviewId(review.getId())
                .createdAt(review.getCreatedAt())
                .build();
    }

    // 리뷰 수정하는 api
    @PutMapping("/{reviewId}")
    @ResponseBody
    public ReviewResponseDTO.UpdateReview updateReview(@RequestBody ReviewRequestDTO request,
                                                       @PathVariable(name = "reviewId") Long reviewId,
                                                       @AuthenticationPrincipal AuthUser authUser){
        User user = authUser.getUser();
        Review review = reviewService.updateReview(user.getId(), reviewId, request);

        return ReviewResponseDTO.UpdateReview.builder()
                .reviewId(review.getId())
                .updatedAt(review.getUpdatedAt())
                .build();
    }

    // 리뷰 삭제하는 api
    @DeleteMapping("/{reviewId}")
    @ResponseBody
    public void deleteReview(@PathVariable(name = "reviewId") Long reviewId,
                             @AuthenticationPrincipal AuthUser authUser){
        User user = authUser.getUser();

        reviewService.deleteReview(user.getId(), reviewId);
    }

    // 리뷰 목록 조회하는 api
    @GetMapping("/{lectureId}")
    @ResponseBody
    public ReviewResponseDTO.ReviewPreViewList getReviews(@PathVariable(name = "lectureId") Long lectureId,
                                                          @RequestParam(name = "page", defaultValue = "1") Integer page){
        Page<Review> reviewList = reviewService.getReviewList(lectureId, page-1);

        return ReviewResponseDTO.ReviewPreViewList.builder()
                .reviewList(reviewList.stream()
                        .map(review -> ReviewResponseDTO.ReviewPreView.builder()
                                .reviewId(review.getId())
                                .ownerNickname(review.getUser().getUsername())
                                .createdAt(review.getCreatedAt())
                                .lecture(review.getLecture().getName())
                                .rate(review.getRate())
                                .build())
                        .collect(Collectors.toList()))
                .listSize(reviewList.getNumberOfElements())
                .totalPage(reviewList.getTotalPages())
                .totalElements(reviewList.getTotalElements())
                .isFirst(reviewList.isFirst())
                .isLast(reviewList.isLast())
                .build();
    }

    // 리뷰 내용 조회 api
    @GetMapping("/{reviewId}/details")
    @ResponseBody
    public ReviewResponseDTO.ReviewDetails getReview(@PathVariable(name = "reviewId") Long reviewId){
        Review review = reviewService.getReview(reviewId);

        return ReviewResponseDTO.ReviewDetails.builder()
                .ownerNickname(review.getUser().getUsername())
                .rate(review.getRate())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .build();
    }
}