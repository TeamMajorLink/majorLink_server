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
    @PostMapping("/{lectureId}/reviews")
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
                                                       @PathVariable(name = "reviewId") Long reviewId){
        Review review = reviewService.updateReview(reviewId, request);

        return ReviewResponseDTO.UpdateReview.builder()
                .reviewId(review.getId())
                .updatedAt(review.getUpdatedAt())
                .build();
    }

    // 리뷰 삭제하는 api
    @DeleteMapping("/{reviewId}")
    @ResponseBody
    public void deleteReview(@PathVariable(name = "reviewId") Long reviewId){
        reviewService.deleteReview(reviewId);
    }

    // 리뷰 조회하는 api
    @GetMapping("/{lectureId}/reviews")
    @ResponseBody
    // 스웨거 세팅 후 파라미터 등 설명 추가
    public ReviewResponseDTO.ReviewPreViewList getReviews(@PathVariable(name = "lectureId") Long lectureId,
                                                          @RequestParam(name = "page") Integer page){
        Page<Review> reviewList = reviewService.getReviewList(lectureId, page-1);

        return ReviewResponseDTO.ReviewPreViewList.builder()
                .reviewList(reviewList.stream()
                        .map(review -> ReviewResponseDTO.ReviewPreView.builder()
                                .ownerNickname(review.getUser().getUsername())
                                .rate(review.getRate())
                                .content(review.getContent())
                                .createdAt(review.getCreatedAt())
                                .build())
                        .collect(Collectors.toList()))
                .listSize(reviewList.getNumberOfElements())
                .totalPage(reviewList.getTotalPages())
                .totalElements(reviewList.getTotalElements())
                .isFirst(reviewList.isFirst())
                .isLast(reviewList.isLast())
                .build();
    }
}
