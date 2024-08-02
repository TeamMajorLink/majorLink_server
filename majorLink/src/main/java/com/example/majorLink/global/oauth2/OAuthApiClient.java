package com.example.majorLink.global.oauth2;

import com.example.majorLink.domain.enums.SocialType;

public interface OAuthApiClient {
    SocialType socialType();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}