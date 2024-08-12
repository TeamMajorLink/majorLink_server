package com.example.majorLink.dto.request;

import com.example.majorLink.domain.Education;
import com.example.majorLink.domain.Project;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class ProfileCardRequest {
    @Size(max=100, message = "한 줄 소개는 100자를 초과할 수 없습니다.")
    private String lineInfo;
    @Size(max=1000, message = "자기소개는 1000자를 초과할 수 없습니다.")
    private String selfInfo;
    private List<Education> educations;
    private List<Project> projects;
    private List<String> skills;
    private String portfolio;
    private String link;
}
