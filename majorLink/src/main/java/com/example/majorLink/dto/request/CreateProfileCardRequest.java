package com.example.majorLink.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateProfileCardRequest {
    @Size(max=100, message = "한 줄 소개는 100자를 초과할 수 없습니다.")
    private String info;
    private String grade;
    private String career;
    private String portfolio;
    private String link;
}
