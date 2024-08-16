package com.example.majorLink.service;

import com.example.majorLink.domain.Lecture;
import com.example.majorLink.domain.User;
import com.example.majorLink.dto.request.LectureRequestDTO;
import org.springframework.data.domain.Page;

public interface LectureService {
    Lecture createLecture(User user, LectureRequestDTO request);
    Lecture updateLecture(User user, Long lectureId, LectureRequestDTO request);
    void DeleteLecture(User user, Long lectureId);

    Page<Lecture> getLectureList(Integer page);
}
