package com.example.majorLink.dto.response;

import com.example.majorLink.dto.request.EducationRequest;
import com.example.majorLink.dto.request.ProjectRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileCardResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lineInfo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String selfInfo;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)     // 리스트가 비어 있다면 조회에서 제외
    private List<EducationResponse> educations;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ProjectResponse> projects;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> skills;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String portfolio;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String link;
}
