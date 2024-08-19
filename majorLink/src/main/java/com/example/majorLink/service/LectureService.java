package com.example.majorLink.service;

import com.example.majorLink.domain.Lecture;
import com.example.majorLink.domain.mapping.TuteeLecture;
import com.example.majorLink.dto.request.LectureRequestDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface LectureService {
    Lecture createLecture(UUID userId, LectureRequestDTO request);
    Lecture updateLecture(UUID userId, LectureRequestDTO request, Long lectureId);
    void DeleteLecture(UUID userId, Long lectureId);

    Page<Lecture> getLectureList(Integer page);

    TuteeLecture addLecture(UUID userId, Long lectureId);
    void cancelLecture(UUID userId, Long lectureId);

    Boolean toggleLike(UUID userId, Long lectureId);

    Page<Lecture> getMostLikedLecture(Integer page);
    Page<Lecture> getNewLecture(Integer page);
    Page<Lecture> getMostRecruitedLecture(Integer page);
    Page<Lecture> getLectureByCategory(Integer page, Long categoryId);
}