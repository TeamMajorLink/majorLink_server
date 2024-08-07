package com.example.majorLink.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FirstLoginResponse {
    private String username;
    private String email;
    private String phone;
    private String profileImg;
    private String gender;
}
