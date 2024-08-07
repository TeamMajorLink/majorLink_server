package com.example.majorLink.controller;

import com.example.majorLink.dto.request.SignInRequest;
import com.example.majorLink.dto.request.SignUpRequest;
import com.example.majorLink.global.auth.AuthTokens;
import com.example.majorLink.global.auth.Tokens;
import com.example.majorLink.global.oauth2.OAuthLoginService;
import com.example.majorLink.repository.UserRepository;
import com.example.majorLink.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final OAuthLoginService oAuthLoginService;
    private final UserService userService;

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

}