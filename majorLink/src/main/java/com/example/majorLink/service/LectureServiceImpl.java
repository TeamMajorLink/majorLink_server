package com.example.majorLink.service;

import com.example.majorLink.domain.Category;
import com.example.majorLink.domain.Lecture;
import com.example.majorLink.domain.Tag;
import com.example.majorLink.domain.User;
import com.example.majorLink.dto.request.LectureRequestDTO;
import com.example.majorLink.repository.CategoryRepository;
import com.example.majorLink.repository.LectureRepository;
import com.example.majorLink.repository.TagRepository;
import com.example.majorLink.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor


public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @Override
    public Lecture createLecture(User user, LectureRequestDTO request) {
        // Category 엔티티 가져오기
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        // Tag 엔티티 가져오기
        Tag tag = tagRepository.findById(request.getTagId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid tag ID"));

        Lecture lecture = Lecture.builder()
                .name(request.getName())
                .body(request.getBody())
                .curri(request.getCurri())
                .info(request.getInfo())
                .level(request.getLevel())
                .pNum(request.getPNum())
                .time(request.getTime())
                .day(request.getDay())
                .startDate(request.getStartDate())
                .exam(request.getExam())
                .category(category)
                .tag(tag)
                .user(user)
                .build();


        return lectureRepository.save(lecture);
    }


    @Override
    public Lecture updateLecture(User user, Long lectureId, LectureRequestDTO request) {

        // 기존의 Lecture 엔티티를 조회
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid lecture ID"));

        // 요청한 사용자가 해당 강의를 개설한 사용자와 일치하는지 확인
        if (!lecture.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("You do not have permission to update this lecture");
        }

        // Category 엔티티 가져오기
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        // Tag 엔티티 가져오기
        Tag tag = tagRepository.findById(request.getTagId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid tag ID"));

        // Lecture 엔티티의 필드 업데이트
        lecture.updateLecture(
                request.getName(),
                request.getBody(),
                request.getCurri(),
                request.getInfo(),
                request.getLevel(),
                request.getPNum(),
                request.getTime(),
                request.getDay(),
                request.getStartDate(),
                request.getExam(),
                category,
                tag
        );

        // Lecture 엔티티 저장 (변경 감지)
        return lectureRepository.save(lecture);
    }

    @Override
    public void DeleteLecture(User user, Long lectureId) {
        // 기존의 Lecture 엔티티를 조회
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid lecture ID"));

        // 요청한 사용자가 해당 강의를 개설한 사용자와 일치하는지 확인
        if (!lecture.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("You do not have permission to delete this lecture");
        }

        // Lecture 엔티티 삭제
        lectureRepository.delete(lecture);
    }

    @Override
    public Page<Lecture> getLectureList(Integer page) {
        Page<Lecture> lecturePage = lectureRepository.findAll(PageRequest.of(page, 10));

        return lecturePage;
    }


}
