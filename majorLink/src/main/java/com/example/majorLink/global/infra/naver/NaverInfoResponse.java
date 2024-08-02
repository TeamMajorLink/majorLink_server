package com.example.majorLink.global.infra.naver;

import com.example.majorLink.domain.enums.SocialType;
import com.example.majorLink.global.oauth2.OAuthInfoResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverInfoResponse implements OAuthInfoResponse {

    @JsonProperty("response")
    private Response response;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Response {
        private String email;
        private String username;
    }

    @Override
    public String getEmail() {
        return response.email;
    }

    @Override
    public String getUsername() {
        return response.username;
    }

    @Override
    public SocialType getSocialType() {
        return SocialType.NAVER;
    }
}