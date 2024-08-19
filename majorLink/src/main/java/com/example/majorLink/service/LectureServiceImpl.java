package com.example.majorLink.service;

import com.example.majorLink.domain.Category;
import com.example.majorLink.domain.Lecture;
import com.example.majorLink.domain.User;
import com.example.majorLink.domain.mapping.Liked;
import com.example.majorLink.domain.mapping.TuteeLecture;
import com.example.majorLink.domain.mapping.TutorLecture;
import com.example.majorLink.dto.request.LectureRequestDTO;
import com.example.majorLink.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor


public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TutorLectureRepository tutorLectureRepository;
    private final TuteeLectureRepository tuteeLectureRepository;
    private final LikedRepository likedRepository;

    // 강의 생성
    @Override
    public Lecture createLecture(UUID userId, LectureRequestDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        // Category 엔티티 가져오기
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        Lecture lecture = Lecture.builder()
                .name(request.getName())
                .body(request.getBody())
                .curri(request.getCurri())
                .info(request.getInfo())
                .level(request.getLevel())
                .pNum(request.getPNum())
                .curPNum(0)
                .time(request.getTime())
                .day(request.getDay())
                .startDate(request.getStartDate())
                .exam(request.getExam())
                .category(category)
                .tag(request.getTag())
                .tutor(user.getNickname())
                .build();

        Lecture saveLecture = lectureRepository.save(lecture);

        TutorLecture tutorLecture = TutorLecture.builder()
                .user(user)
                .lecture(saveLecture)
                .build();

        tutorLectureRepository.save(tutorLecture);

        return saveLecture;
    }

    // 강의 수정
    @Override
    public Lecture updateLecture(UUID userId, LectureRequestDTO request, Long lectureId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        TutorLecture tutorLecture = tutorLectureRepository.findByLectureId(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid lecture ID"));

        // 기존의 Lecture 엔티티를 조회
        Lecture lecture = tutorLecture.getLecture();

        UUID tutorId = tutorLecture.getUser().getId();

        // 요청한 사용자가 해당 강의를 개설한 사용자와 일치하는지 확인
        if (!tutorId.equals(user.getId())) {
            throw new IllegalStateException("You do not have permission to update this lecture");
        }

        // Category 엔티티 가져오기
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

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
                request.getTag()
        );

        // Lecture 엔티티 저장 (변경 감지)
        return lectureRepository.save(lecture);
    }

    // 강의 삭제
    @Override
    public void DeleteLecture(UUID userId, Long lectureId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        TutorLecture tutorLecture = tutorLectureRepository.findByLectureId(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid lecture ID"));

        // 기존의 Lecture 엔티티를 조회
        Lecture lecture = tutorLecture.getLecture();
        UUID tutorId = tutorLecture.getUser().getId();

        // 요청한 사용자가 해당 강의를 개설한 사용자와 일치하는지 확인
        if (!tutorId.equals(user.getId())) {
            throw new IllegalStateException("You do not have permission to delete this lecture");
        }

        // Lecture 엔티티 삭제
        lectureRepository.delete(lecture);
    }

    // 강의 목록 조회
    @Override
    public Page<Lecture> getLectureList(Integer page) {
        Page<Lecture> lecturePage = lectureRepository.findAll(PageRequest.of(page, 10));

        return lecturePage;
    }

    // 강의 수강 신청
    @Override
    public TuteeLecture addLecture(UUID userId, Long lectureId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid lecture ID"));

        if (lecture.getPNum() >= lecture.getCurPNum()) {
            throw new IllegalStateException("The lecture is full");
        }

        TuteeLecture tuteeLecture = TuteeLecture.builder()
                .user(user)
                .lecture(lecture)
                .build();

        lecture.addCurPNum();

        return tuteeLectureRepository.save(tuteeLecture);
    }

    // 강의 수강 취소
    @Override
    public void cancelLecture(UUID userId, Long lectureId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid lecture ID"));

        TuteeLecture tuteeLecture = tuteeLectureRepository.findByUserIdAndLectureId(userId, lectureId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID or lecture ID"));

        lecture.subCurPNum();

        tuteeLectureRepository.delete(tuteeLecture);
    }

    // 강의 좋아요
    @Override
    public Boolean toggleLike(UUID userId, Long lectureId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid lecture ID"));

        Boolean alreadyLiked = likedRepository.existsByUserAndLecture(user, lecture);

        if (alreadyLiked) {
            // 좋아요 취소
            likedRepository.deleteByUserAndLecture(user, lecture);
            return false;
        } else {
            // 좋아요
            Liked liked = Liked.builder()
                    .user(user)
                    .lecture(lecture)
                    .build();

            likedRepository.save(liked);

            return true;
        }
    }

    // 좋아요 많은 강의 조회
    @Override
    public Page<Lecture> getMostLikedLecture(Integer page) {

        return lectureRepository.orderByLikedCount(PageRequest.of(page, 10));
    }

    // 새로 등록된 클래스 조회
    @Override
    public Page<Lecture> getNewLecture(Integer page) {

        return lectureRepository.orderByCreatedAt(PageRequest.of(page, 10));
    }

    // 모집인원 많은 클래스 조회
    @Override
    public Page<Lecture> getMostRecruitedLecture(Integer page) {

        return lectureRepository.orderByCurPNum(PageRequest.of(page, 10));
    }

    // 카테고리별 클래스 조회
    @Override
    public Page<Lecture> getLectureByCategory(Integer page, Long categoryId) {

        return lectureRepository.orderByCategoryId(categoryId, PageRequest.of(page, 10));
    }
}