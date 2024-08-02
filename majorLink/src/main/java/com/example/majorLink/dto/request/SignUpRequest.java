package com.example.majorLink.dto.request;

import com.example.majorLink.domain.enums.Gender;
import com.example.majorLink.domain.enums.LearnPart;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequest {
    private String provider;
    private String providerId;
    private String accessToken;
    private String username;
    private String email;
    private String phone;
    private String learnPart;
    private String firstMajor;
    private String secondMajor;
    private String favorite;
    private String role;
    private String gender;
    private String profileImg;
}
