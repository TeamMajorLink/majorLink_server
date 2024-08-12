package com.example.majorLink.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectResponse {
    private Long projectId;
    private String projectName;
    private String space;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String start;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String end;
    private Boolean checkStatus;
    private String projectDescript;
}
