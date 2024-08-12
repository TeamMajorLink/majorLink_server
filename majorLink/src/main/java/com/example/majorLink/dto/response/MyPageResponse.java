package com.example.majorLink.dto.response;

import com.example.majorLink.domain.enums.Gender;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MyPageResponse {
    private String profileImg;
    private String username;
    private String nickname;
    private String birth;
    private String email;
    private String firstMajor;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String secondMajor;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String favorite;
    private Gender gender;
    private Integer point;
}
