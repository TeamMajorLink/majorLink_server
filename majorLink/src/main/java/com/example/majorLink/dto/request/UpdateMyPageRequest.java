package com.example.majorLink.dto.request;

import com.example.majorLink.domain.enums.Gender;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UpdateMyPageRequest {
    private String profileImg;
    private String username;
    @Size(min = 2, message = "닉네임을 2자 이상 입력해주세요.")
    private String nickname;
    @Size(max = 8, message = "생년월일은 8자를 초과할 수 없습니다.")
    private String birth;
    private String email;
    private String password;
    private String firstMajor;
    private String secondMajor;
    private String favorite;
    private Gender gender;

}
