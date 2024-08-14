package com.example.majorLink.controller;

import com.example.majorLink.domain.Lecture;
import com.example.majorLink.domain.User;
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

        Lecture lecture = lectureService.createLecture(user, request);

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

        Lecture lecture = lectureService.updateLecture(user, lectureId, request);

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

        lectureService.DeleteLecture(user, lectureId);
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

}
