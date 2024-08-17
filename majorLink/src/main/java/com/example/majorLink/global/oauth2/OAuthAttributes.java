package com.example.majorLink.global.oauth2;

import com.example.majorLink.domain.enums.Gender;
import com.example.majorLink.domain.enums.SocialType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Objects;

@Builder
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class OAuthAttributes {
    @JsonInclude
    private String id;
    private String username;
    private String email;
    private String profileImg;
    private Gender gender;
    private String phone;
    private String accessToken;

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public static OAuthAttributes of(SocialType provider, String userNameAttribute, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get(userNameAttribute);

        if (provider == SocialType.NAVER) {
            return ofNaver(response);
        }

        return null;
    }

    public static OAuthAttributes ofNaver(Map<String, Object> response) {
        OAuthAttributes oAuthAttributes = new OAuthAttributes();
//        oAuthAttributes.id = (String) response.get("id");
//        oAuthAttributes.email = (String) response.get("email");
//        oAuthAttributes.username = (String) response.get("name");
//        oAuthAttributes.phone = (String) response.get("phone");
//        oAuthAttributes.profileImg = (String) response.get("profile_image");
        oAuthAttributes.accessToken = (String) response.get("accessToken");
//        String gender = (String) response.get("gender");
//
//        if ("F".equals(gender)) {
//            oAuthAttributes.gender = Gender.FEMALE;
//        } else if ("M".equals(gender)) {
//            oAuthAttributes.gender = Gender.MALE;
//        }

        return oAuthAttributes;
    }
}