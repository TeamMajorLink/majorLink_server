package com.example.majorLink.dto.request;

import com.example.majorLink.domain.enums.CheckStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class EducationRequest {
    @NotNull(message = "교육의 id 값을 알아야 수정할 수 있습니다.")
    private Long id;
    private String eduName;
    private String process;
    @Size(max = 10, message = "시작일은 10글자를 넘을 수 없습니다.")
    private String start;
    @Size(max = 10, message = "종료일은 10글자를 넘을 수 없습니다.")
    private String end;
    private Boolean checkStatus = false;
}
