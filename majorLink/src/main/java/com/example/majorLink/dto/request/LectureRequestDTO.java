package com.example.majorLink.dto.request;

import com.example.majorLink.domain.enums.Day;
import com.example.majorLink.domain.enums.Exam;
import com.example.majorLink.domain.enums.Level;
import lombok.Getter;

import java.time.LocalTime;
import java.util.Date;

@Getter
public class LectureRequestDTO {
    // 새로운 강의 등록할때 필요한 정보들 작성
    String name;
    String body; // 강의 개요
    int curri; // 강의 횟수
    String info; // 강의 정보
    Level level;
    int pNum;
    LocalTime time;
    Day day; // 요일
    Date startDate;
    Exam exam; // 시험 유무
    Long categoryId;
    String tag;
}
