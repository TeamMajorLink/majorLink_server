package com.example.majorLink.global.oauth2;

import com.example.majorLink.domain.enums.SocialType;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    SocialType socialType();
    MultiValueMap<String, String> makeBody();
}
