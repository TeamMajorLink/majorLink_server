package com.example.majorLink.controller;

import com.example.majorLink.domain.User;
import com.example.majorLink.dto.request.ProfileCardRequest;
import com.example.majorLink.dto.response.ProfileCardResponse;
import com.example.majorLink.global.auth.AuthUser;
import com.example.majorLink.service.ProfileCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile-card")
public class ProfileCardController {
    private final ProfileCardService profileCardService;

    /**
     * 프로필 카드 등록 API
     * [POST] /profile-card
     * @param authUser
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<?> createProfileCard(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody ProfileCardRequest request
            ) {
        User user = authUser.getUser();
        profileCardService.createProfileCard(user, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 프로필 카드 수정 API
     * [PATCH] /profile-card
     * @param authUser
     * @param request
     * @return
     */
    @PatchMapping
    public ResponseEntity<?> modifyProfileCard(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody ProfileCardRequest request
    ) {
        User user = authUser.getUser();
        profileCardService.modifyProfileCard(user, request);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 프로필 카드 조회 API
     * [GET] /profile-card?userId={userId}
     * @param authUser
     * @param userId
     * @return
     */
    @GetMapping
    public ResponseEntity<ProfileCardResponse> getProfileCard(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam(name = "userId", required = false) UUID userId        // userId가 빈 경우를 대비해 쿼리 파라미터 사용
    ) {
        User user = null;
        if (authUser != null) {
            user = authUser.getUser();
        }
        ProfileCardResponse profileCardResponse = profileCardService.getProfileCard(user, userId);
        return ResponseEntity.ok()
                .body(profileCardResponse);
    }
}
