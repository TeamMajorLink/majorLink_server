package com.example.majorLink.dto.response;

import com.example.majorLink.domain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ProfileResponse {
    private String profileImg;
    private String username;
    private String email;
    private String firstMajor;
    private String secondMajor;
    private String favorite;
    private Gender gender;
    private Integer point;
}
