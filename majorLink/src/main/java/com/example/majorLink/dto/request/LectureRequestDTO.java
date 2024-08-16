package com.example.majorLink.dto.request;

import com.example.majorLink.domain.Category;
import com.example.majorLink.domain.Tag;
import com.example.majorLink.domain.enums.Day;
import com.example.majorLink.domain.enums.Exam;
import com.example.majorLink.domain.enums.Level;
import lombok.Getter;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@Getter
public class LectureRequestDTO {
    // 새로운 강의 등록할때 필요한 정보들 작성
    String name;
    String body;
    int curri;
    String info;
    Level level;
    int pNum;
    LocalTime time;
    Day day;
    Date startDate;
    Exam exam;
    Long categoryId;
    Long tagId;
    UUID userId;

}
