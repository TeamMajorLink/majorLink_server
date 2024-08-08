package com.example.majorLink.service;

import com.example.majorLink.dto.request.SignInRequest;
import com.example.majorLink.dto.request.SignUpRequest;
import com.example.majorLink.global.auth.Tokens;

public interface UserService{
    Tokens signIn(SignInRequest request);
    Tokens signUp(SignUpRequest request);
}