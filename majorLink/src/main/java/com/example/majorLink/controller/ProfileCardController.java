package com.example.majorLink.controller;

import com.example.majorLink.domain.User;
import com.example.majorLink.dto.request.CreateProfileCardRequest;
import com.example.majorLink.global.auth.AuthUser;
import com.example.majorLink.service.ProfileCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile-card")
public class ProfileCardController {
    private final ProfileCardService profileCardService;

    @PostMapping
    public ResponseEntity<?> createProfileCard(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody CreateProfileCardRequest request
            ) {
        User user = authUser.getUser();
        profileCardService.createProfileCard(user, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
