package com.example.majorLink.service;

import com.example.majorLink.domain.Lecture;
import com.example.majorLink.domain.mapping.TuteeLecture;
import com.example.majorLink.dto.request.LectureRequestDTO;
import com.example.majorLink.dto.response.LectureResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface LectureService {
    Lecture createLecture(UUID userId, LectureRequestDTO request);
    Lecture updateLecture(UUID userId, LectureRequestDTO request, Long lectureId);
    void DeleteLecture(UUID userId, Long lectureId);

    Page<Lecture> getLectureList(Integer page);
    Lecture getLecture(Long lectureId);

    TuteeLecture addLecture(UUID userId, Long lectureId);

    Boolean toggleLike(UUID userId, Long lectureId);

    Page<Lecture> getMostLikedLecture(Integer page);
    Page<Lecture> getNewLecture(Integer page);
    Page<Lecture> getMostRecruitedLecture(Integer page);
    Page<Lecture> getLectureByCategory(Integer page, Long categoryId);
    Page<Lecture> getLectureByLevel(int i, String level);

    List<LectureResponseDTO.CategoryResponseDTO> getAllCategories();
}