package com.example.majorLink.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResponseDTO {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateReview {
        Long reviewId;
        String ownerNickname;
        String content;
        int rate;
        String lectue;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewPreView {
        Long reviewId;
        String ownerNickname;
        int rate;
        String content;
        String lecture;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewDetails {
        String ownerNickname;
        int rate;
        String content;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewPreViewList {
        List<ReviewPreView> reviewList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateReview {
        Long reviewId;
        String ownerNickname;
        String content;
        int rate;
        String lectue;
        LocalDateTime updatedAt;
    }
}
