package com.example.majorLink.controller;

import com.example.majorLink.domain.Lecture;
import com.example.majorLink.domain.User;
import com.example.majorLink.domain.mapping.TuteeLecture;
import com.example.majorLink.dto.request.LectureRequestDTO;
import com.example.majorLink.dto.response.LectureResponseDTO;
import com.example.majorLink.global.auth.AuthUser;
import com.example.majorLink.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lecture")
public class LectureController {

    private final LectureService lectureService;

    // 강의 생성 api
    @PostMapping("/create")
    @ResponseBody
    public LectureResponseDTO.CreateLecture createReview(@RequestBody LectureRequestDTO request,
                                                         @AuthenticationPrincipal AuthUser authUser){

        User user = authUser.getUser();

        Lecture lecture = lectureService.createLecture(user.getId(), request);

        return LectureResponseDTO.CreateLecture.builder()
                .lectureId(lecture.getId())
                .createdAt(lecture.getCreatedAt())
                .build();
    }

    // 강의 수정 api
    @PutMapping("/{lectureId}")
    @ResponseBody
    public LectureResponseDTO.UpdateLecture updateLecture(@RequestBody LectureRequestDTO request,
                                                          @AuthenticationPrincipal AuthUser authUser,
                                                          @PathVariable(name = "lectureId") Long lectureId){
        User user = authUser.getUser();

        Lecture lecture = lectureService.updateLecture(user.getId(), request, lectureId);

        return LectureResponseDTO.UpdateLecture.builder()
                .lectureId(lecture.getId())
                .updatedAt(lecture.getUpdatedAt())
                .build();
    }

    // 강의 삭제 api
    @DeleteMapping("/{lectureId}")
    @ResponseBody
    public void deleteLecture(@PathVariable(name = "lectureId") Long lectureId,
                              @AuthenticationPrincipal AuthUser authUser){

        User user = authUser.getUser();

        lectureService.DeleteLecture(user.getId(), lectureId);
    }

    // 강의 list 조회 api
    @GetMapping("/list")
    @ResponseBody
    // 스웨거 세팅 후 파라미터 등 설명 추가
    public LectureResponseDTO.LecturePreViewList getLectures(@RequestParam(name = "page") Integer page){
        Page<Lecture> lectureList = lectureService.getLectureList(page-1);

        return LectureResponseDTO.LecturePreViewList.builder()
                .lectureList(lectureList.stream()
                        .map(lecture -> LectureResponseDTO.LecturePreView.builder()
                                .name(lecture.getName())
                                .build())
                        .collect(Collectors.toList()))
                .listSize(lectureList.getNumberOfElements())
                .totalPage(lectureList.getTotalPages())
                .totalElements(lectureList.getTotalElements())
                .isFirst(lectureList.isFirst())
                .isLast(lectureList.isLast())
                .build();
    }

    // 강의 수강 신청 api
    @PostMapping("/{lectureId}/register")
    @ResponseBody
    public LectureResponseDTO.CreateTuteeLecture createTuteeLecture(@PathVariable(name = "lectureId") Long lectureId,
                                                                    @AuthenticationPrincipal AuthUser authUser){
        User user = authUser.getUser();

        TuteeLecture tuteeLecture = lectureService.addLecture(user.getId(), lectureId);

        return LectureResponseDTO.CreateTuteeLecture.builder()
                .tuteeLectureId(tuteeLecture.getId())
                .createdAt(user.getCreatedAt())
                .build();
    }

    // 강의 취소 api
    @DeleteMapping("/{lectureId}/cancel")
    @ResponseBody
    public void cancelLecture(@PathVariable(name = "lectureId") Long lectureId,
                              @AuthenticationPrincipal AuthUser authUser){
        User user = authUser.getUser();

        lectureService.cancelLecture(user.getId(), lectureId);
    }

    // 강의 좋아요 토글 api
    @PostMapping("/{lectureId}/like")
    @ResponseBody
    public Boolean toggleLike(@PathVariable(name = "lectureId") Long lectureId,
                              @AuthenticationPrincipal AuthUser authUser){
        User user = authUser.getUser();

        return lectureService.toggleLike(user.getId(), lectureId);
    }

    // 좋아요 많은 강의 조회 api
    @GetMapping("/mostLiked")
    @ResponseBody
    public LectureResponseDTO.LecturePreViewList getMostLikedLectures(@RequestParam(name = "page") Integer page){
        Page<Lecture> lectureList = lectureService.getMostLikedLecture(page-1);

        return LectureResponseDTO.LecturePreViewList.builder()
                .lectureList(lectureList.stream()
                        .map(lecture -> LectureResponseDTO.LecturePreView.builder()
                                .name(lecture.getName())
                                .build())
                        .collect(Collectors.toList()))
                .listSize(lectureList.getNumberOfElements())
                .totalPage(lectureList.getTotalPages())
                .totalElements(lectureList.getTotalElements())
                .isFirst(lectureList.isFirst())
                .isLast(lectureList.isLast())
                .build();
    }

    // 최신 강의 조회 api
    @GetMapping("/new")
    @ResponseBody
    public LectureResponseDTO.LecturePreViewList getNewLectures(@RequestParam(name = "page") Integer page){
        Page<Lecture> lectureList = lectureService.getNewLecture(page-1);

        return LectureResponseDTO.LecturePreViewList.builder()
                .lectureList(lectureList.stream()
                        .map(lecture -> LectureResponseDTO.LecturePreView.builder()
                                .name(lecture.getName())
                                .build())
                        .collect(Collectors.toList()))
                .listSize(lectureList.getNumberOfElements())
                .totalPage(lectureList.getTotalPages())
                .totalElements(lectureList.getTotalElements())
                .isFirst(lectureList.isFirst())
                .isLast(lectureList.isLast())
                .build();
    }

    // 가장 많이 모집된 강의 조회 api
    @GetMapping("/mostRecruited")
    @ResponseBody
    public LectureResponseDTO.LecturePreViewList getMostRecruitedLectures(@RequestParam(name = "page") Integer page){
        Page<Lecture> lectureList = lectureService.getMostRecruitedLecture(page-1);

        return LectureResponseDTO.LecturePreViewList.builder()
                .lectureList(lectureList.stream()
                        .map(lecture -> LectureResponseDTO.LecturePreView.builder()
                                .name(lecture.getName())
                                .build())
                        .collect(Collectors.toList()))
                .listSize(lectureList.getNumberOfElements())
                .totalPage(lectureList.getTotalPages())
                .totalElements(lectureList.getTotalElements())
                .isFirst(lectureList.isFirst())
                .isLast(lectureList.isLast())
                .build();
    }

    // 카테고리별 강의 조회 api
    @GetMapping("/{categoryId}")
    @ResponseBody
    public LectureResponseDTO.LecturePreViewList getLecturesByCategory(@RequestParam(name = "page") Integer page,
                                                                       @PathVariable(name = "categoryId") Long categoryId){
        Page<Lecture> lectureList = lectureService.getLectureByCategory(page-1, categoryId);

        return LectureResponseDTO.LecturePreViewList.builder()
                .lectureList(lectureList.stream()
                        .map(lecture -> LectureResponseDTO.LecturePreView.builder()
                                .name(lecture.getName())
                                .build())
                        .collect(Collectors.toList()))
                .listSize(lectureList.getNumberOfElements())
                .totalPage(lectureList.getTotalPages())
                .totalElements(lectureList.getTotalElements())
                .isFirst(lectureList.isFirst())
                .isLast(lectureList.isLast())
                .build();
    }
}
