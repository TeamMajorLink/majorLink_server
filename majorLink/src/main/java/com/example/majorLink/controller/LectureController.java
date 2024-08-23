package com.example.majorLink.controller;

import com.example.majorLink.domain.Lecture;
import com.example.majorLink.domain.User;
import com.example.majorLink.domain.enums.Level;
import com.example.majorLink.domain.mapping.TuteeLecture;
import com.example.majorLink.dto.request.LectureRequestDTO;
import com.example.majorLink.dto.response.LectureResponseDTO;
import com.example.majorLink.global.auth.AuthUser;
import com.example.majorLink.repository.ReviewRepository;
import com.example.majorLink.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lecture")
public class LectureController {

    private final LectureService lectureService;
    private final ReviewRepository reviewRepository;

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

    // 강의 목록 조회 api
    @GetMapping("/list")
    @ResponseBody
    // 스웨거 세팅 후 파라미터 등 설명 추가
    public LectureResponseDTO.LecturePreViewList getLectures(@RequestParam(name = "page", defaultValue = "1") Integer page){
        Page<Lecture> lectureList = lectureService.getLectureList(page-1);

        return LectureResponseDTO.LecturePreViewList.builder()
                .lectureList(lectureList.stream()
                        .map(lecture -> {

                            Double avgRate = reviewRepository.findAverageRatingByLectureId(lecture.getId());

                            return LectureResponseDTO.LecturePreView.builder()
                                    .lectureId(lecture.getId())
                                    .name(lecture.getName())
                                    .cNum(lecture.getCNum())
                                    .pNum(lecture.getPNum())
                                    .imageUrl(lecture.getImgUrl())
                                    .mainCategory(lecture.getCategory().getMainCategory())
                                    .subCategory(lecture.getSubCategory())
                                    .avgRate(String.format("%.1f", avgRate))
                                    .build();
                        })
                        .collect(Collectors.toList()))
                .listSize(lectureList.getNumberOfElements())
                .totalPage(lectureList.getTotalPages())
                .totalElements(lectureList.getTotalElements())
                .isFirst(lectureList.isFirst())
                .isLast(lectureList.isLast())
                .build();
    }

    // 강의 상세페이지
    @GetMapping("/{lectureId}/details")
    @ResponseBody
    public LectureResponseDTO.LectureDetails getLecture(@PathVariable(name = "lectureId") Long lectureId){
        Lecture lecture = lectureService.getLecture(lectureId);

        Double avgRate = reviewRepository.findAverageRatingByLectureId(lecture.getId());

        return LectureResponseDTO.LectureDetails.builder()
                .name(lecture.getName())
                .body(lecture.getBody())
                .curri(lecture.getCurri())
                .info(lecture.getInfo())
                .level(lecture.getLevel().name())
                .cNum(lecture.getCNum())
                .pNum(lecture.getPNum())
                .time(lecture.getTime().toString())
                .day(lecture.getDay().name())
                .startDate(lecture.getStartDate())
                .exam(lecture.getExam().name())
                .tag(lecture.getTag())
                .tutor(lecture.getTutor())
                .imageUrl(lecture.getImgUrl())
                .mainCategory(lecture.getCategory().getMainCategory())
                .subCategory(lecture.getSubCategory())
                .avgRate(String.format("%.1f", avgRate))
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
    public LectureResponseDTO.LecturePreViewList getMostLikedLectures(@RequestParam(name = "page", defaultValue = "1") Integer page){
        Page<Lecture> lectureList = lectureService.getMostLikedLecture(page-1);


        return LectureResponseDTO.LecturePreViewList.builder()
                .lectureList(lectureList.stream()
                        .map(lecture -> {

                            Double avgRate = reviewRepository.findAverageRatingByLectureId(lecture.getId());

                            return LectureResponseDTO.LecturePreView.builder()
                                    .lectureId(lecture.getId())
                                    .name(lecture.getName())
                                    .mainCategory(lecture.getCategory().getMainCategory())
                                    .subCategory(lecture.getSubCategory())
                                    .cNum(lecture.getCNum())
                                    .pNum(lecture.getPNum())
                                    .imageUrl(lecture.getImgUrl())
                                    .avgRate(String.format("%.1f", avgRate))
                                    .build();
                        })
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
    public LectureResponseDTO.LecturePreViewList getNewLectures(@RequestParam(name = "page", defaultValue = "1") Integer page){
        Page<Lecture> lectureList = lectureService.getNewLecture(page-1);

        return LectureResponseDTO.LecturePreViewList.builder()
                .lectureList(lectureList.stream()
                        .map(lecture -> {

                            Double avgRate = reviewRepository.findAverageRatingByLectureId(lecture.getId());

                            return LectureResponseDTO.LecturePreView.builder()
                                    .lectureId(lecture.getId())
                                    .name(lecture.getName())
                                    .mainCategory(lecture.getCategory().getMainCategory())
                                    .subCategory(lecture.getSubCategory())
                                    .cNum(lecture.getCNum())
                                    .pNum(lecture.getPNum())
                                    .imageUrl(lecture.getImgUrl())
                                    .avgRate(String.format("%.1f", avgRate))
                                    .build();
                        })
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
    public LectureResponseDTO.LecturePreViewList getMostRecruitedLectures(@RequestParam(name = "page", defaultValue = "1") Integer page){
        Page<Lecture> lectureList = lectureService.getMostRecruitedLecture(page-1);

        return LectureResponseDTO.LecturePreViewList.builder()
                .lectureList(lectureList.stream()
                        .map(lecture -> {
                            Double avgRate = reviewRepository.findAverageRatingByLectureId(lecture.getId());

                            return LectureResponseDTO.LecturePreView.builder()
                                    .lectureId(lecture.getId())
                                    .name(lecture.getName())
                                    .mainCategory(lecture.getCategory().getMainCategory())
                                    .subCategory(lecture.getSubCategory())
                                    .cNum(lecture.getCNum())
                                    .pNum(lecture.getPNum())
                                    .imageUrl(lecture.getImgUrl())
                                    .avgRate(String.format("%.1f", avgRate))
                                    .build();
                        })
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
    public LectureResponseDTO.LecturePreViewList getLecturesByCategory(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                                       @PathVariable(name = "categoryId") Long categoryId){
        Page<Lecture> lectureList = lectureService.getLectureByCategory(page-1, categoryId);

        return LectureResponseDTO.LecturePreViewList.builder()
                .lectureList(lectureList.stream()
                        .map(lecture -> {
                            Double avgRate = reviewRepository.findAverageRatingByLectureId(lecture.getId());

                            return LectureResponseDTO.LecturePreView.builder()
                                    .lectureId(lecture.getId())
                                    .name(lecture.getName())
                                    .mainCategory(lecture.getCategory().getMainCategory())
                                    .subCategory(lecture.getSubCategory())
                                    .cNum(lecture.getCNum())
                                    .pNum(lecture.getPNum())
                                    .imageUrl(lecture.getImgUrl())
                                    .avgRate(String.format("%.1f", avgRate))
                                    .build();
                        })
                        .collect(Collectors.toList()))
                .listSize(lectureList.getNumberOfElements())
                .totalPage(lectureList.getTotalPages())
                .totalElements(lectureList.getTotalElements())
                .isFirst(lectureList.isFirst())
                .isLast(lectureList.isLast())
                .build();
    }

    // 카테고리, 레벨 조회 api
    @GetMapping("/categories")
    @ResponseBody
    public LectureResponseDTO.CategoryAndLevelList getCategoryList() {
        List<LectureResponseDTO.CategoryResponseDTO> categories = lectureService.getAllCategories();

        List<String> levels = Arrays.stream(Level.values())
                .map(Level::name)
                .collect(Collectors.toList());

        return LectureResponseDTO.CategoryAndLevelList.builder()
                .categoryList(categories)
                .levelList(levels)
                .build();
    }

    // 레벨별 강의 조회 api
    @GetMapping("/{level}")
    @ResponseBody
    public LectureResponseDTO.LecturePreViewList getLecturesByLevel(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                                    @PathVariable(name = "level") String level){
        Page<Lecture> lectureList = lectureService.getLectureByLevel(page-1, level);

        return LectureResponseDTO.LecturePreViewList.builder()
                .lectureList(lectureList.stream()
                        .map(lecture -> {
                            Double avgRate = reviewRepository.findAverageRatingByLectureId(lecture.getId());

                            return LectureResponseDTO.LecturePreView.builder()
                                    .lectureId(lecture.getId())
                                    .name(lecture.getName())
                                    .mainCategory(lecture.getCategory().getMainCategory())
                                    .subCategory(lecture.getSubCategory())
                                    .cNum(lecture.getCNum())
                                    .pNum(lecture.getPNum())
                                    .imageUrl(lecture.getImgUrl())
                                    .avgRate(String.format("%.1f", avgRate))
                                    .build();
                        })
                        .collect(Collectors.toList()))
                .listSize(lectureList.getNumberOfElements())
                .totalPage(lectureList.getTotalPages())
                .totalElements(lectureList.getTotalElements())
                .isFirst(lectureList.isFirst())
                .isLast(lectureList.isLast())
                .build();
    }

}
