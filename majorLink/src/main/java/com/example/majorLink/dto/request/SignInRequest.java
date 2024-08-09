package com.example.majorLink.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    String provider;
    String providerId;
    String accessToken;
}
