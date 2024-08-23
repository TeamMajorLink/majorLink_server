package com.example.majorLink.controller;

import com.example.majorLink.domain.User;
import com.example.majorLink.dto.request.SignInRequest;
import com.example.majorLink.dto.request.SignUpRequest;
import com.example.majorLink.dto.request.UpdateMyPageRequest;
import com.example.majorLink.dto.response.MyPageResponse;
import com.example.majorLink.global.auth.AuthUser;
import com.example.majorLink.global.auth.Tokens;
import com.example.majorLink.global.oauth2.OAuthLoginService;
import com.example.majorLink.repository.UserRepository;
import com.example.majorLink.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final OAuthLoginService oAuthLoginService;
    private final UserService userService;

    /**
     * 회원가입 API
     * [POST] /users/sign-up
     * @param signUpRequest
     * @return
     */
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        Tokens tokens = userService.signUp(signUpRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", tokens.getAccessToken());
        headers.set("refresh-token", tokens.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .build();
    }

    /**
     * 로그인 API
     * [POST] /users/sign-in
     * @param signInRequest
     * @return
     */
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) {
        Tokens tokens = userService.signIn(signInRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", tokens.getAccessToken());
        headers.set("refresh-token", tokens.getRefreshToken());

        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }

    /**
     * 마이페이지 조회 API
     * [GET] /users/my-page
     * @param authUser
     * @return
     */
    @GetMapping("/my-page")
    public ResponseEntity<MyPageResponse> getMyPage(@AuthenticationPrincipal AuthUser authUser) {
        MyPageResponse myPageResponse = userService.getMyPage(authUser.getUser());

        return ResponseEntity.ok()
                .body(myPageResponse);
    }

    /**
     * 마이페이지 수정 API
     * [PATCH] /users/my-page
     * @param authUser
     * @param updateMyPageRequest
     * @return
     */
    @PatchMapping("/my-page")
    public ResponseEntity<?> modifyMyPage(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody UpdateMyPageRequest updateMyPageRequest) {
        User user = authUser.getUser();
        userService.modifyMyPage(user, updateMyPageRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .build();

    }

    //uuid 뽑는 api
    @GetMapping("/get-uuid")
    public UUID getUuid(@AuthenticationPrincipal AuthUser authUser) {
        User user = authUser.getUser();
        return user.getId();
    }

}