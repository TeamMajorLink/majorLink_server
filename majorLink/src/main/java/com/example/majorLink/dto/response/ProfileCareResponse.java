package com.example.majorLink.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ProfileCareResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String info;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String grade;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String career;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String portfolio;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String link;
}
