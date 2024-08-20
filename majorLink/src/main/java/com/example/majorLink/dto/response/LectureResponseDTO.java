package com.example.majorLink.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class LectureResponseDTO {
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateLecture {
        Long lectureId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LecturePreView {
        Long lectureId;
        String name;
        Integer cNum;
        Integer pNum;
        String mainCategory;
        String subCategory;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LectureDetails {
        String name;
        String body;
        Integer curri;
        String info;
        String level;
        Integer cNum;
        Integer pNum;
        String time;
        String day;
        Date startDate;
        String exam;
        String tag;
        String tutor;
        String mainCategory;
        String subCategory;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LecturePreViewList {
        List<LecturePreView> lectureList;
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
    public static class UpdateLecture {
        Long lectureId;
        LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateTuteeLecture {
        Long tuteeLectureId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MainCategory {
        List<String> mainCategoryList;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SubCategory {
        List<String> subCategoryList;
    }
}
