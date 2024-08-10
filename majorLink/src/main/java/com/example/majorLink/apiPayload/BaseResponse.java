package com.example.majorLink.apiPayload;

import com.example.majorLink.apiPayload.code.status.SuccessStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class BaseResponse<T> {

    @JsonProperty("isSuccess")
    private final boolean isSuccess;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;



    //성공한 경우
    public static <T> BaseResponse<T> onSuccess(T result) {
        return new BaseResponse<>(true, SuccessStatus._OK.getCode(), SuccessStatus._CREATE.getMessage(), T);

    }
}
