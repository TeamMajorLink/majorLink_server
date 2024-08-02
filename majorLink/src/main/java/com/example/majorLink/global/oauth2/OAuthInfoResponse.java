package com.example.majorLink.global.oauth2;

import com.example.majorLink.domain.enums.SocialType;

public interface OAuthInfoResponse {
    String getEmail();
    String getUsername();
    SocialType getSocialType();
}