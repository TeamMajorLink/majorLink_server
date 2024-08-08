package com.example.majorLink.global.auth;

import com.example.majorLink.dto.response.FirstLoginResponse;
import com.example.majorLink.global.oauth2.OAuthLoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final OAuthLoginService oAuthLoginService;
    private final ObjectMapper objectMapper;

    @GetMapping("/result/member")
    public void returnLoginResult(HttpServletResponse response,
                                  @RequestParam(name = "X-Auth-Token") String XAuthToken,
                                  @RequestParam(name = "refresh-Token") String refreshToken) {
        response.setHeader("X-Auth-Token", XAuthToken);
        response.setHeader("refresh-Token", refreshToken);
    }

    @GetMapping("/result/new-user")
    public void returnLoginResult(HttpServletResponse response,
                                  @RequestParam(value = "username") String username,
                                  @RequestParam(value = "email") String email,
                                  @RequestParam(value = "profileImg") String profileImg,
                                  @RequestParam(value = "phone") String phone,
                                  @RequestParam(value = "gender", required = false) String gender) throws IOException {
        FirstLoginResponse firstLogInResponse = FirstLoginResponse.builder()
                .username(username)
                .email(email)
                .phone(phone)
                .profileImg(profileImg)
                .gender(gender)
                .build();

//        response.setHeader("temp-token", tempToken);
        response.getWriter().write(objectMapper.writeValueAsString(firstLogInResponse));
    }

//    @PostMapping("/reissue-token")
//    public ResponseEntity reissueToken(@RequestHeader("X-AUTH-TOKEN") String accessToken,
//                                       @RequestHeader("refresh-Token") String refreshToken) {
//        AuthTokens tokens = authTokensGenerator.reissueToken(accessToken, refreshToken);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("X-AUTH-TOKEN", tokens.getAccessToken());
//        headers.add("refresh-token", tokens.getRefreshToken());
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .headers(headers)
//                .build();
//    }
}