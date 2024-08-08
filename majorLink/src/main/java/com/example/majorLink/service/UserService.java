package com.example.majorLink.service;

import com.example.majorLink.domain.User;
import com.example.majorLink.dto.request.SignInRequest;
import com.example.majorLink.dto.request.SignUpRequest;
import com.example.majorLink.dto.request.UpdateProfileRequest;
import com.example.majorLink.dto.response.ProfileResponse;
import com.example.majorLink.global.auth.Tokens;
import org.springframework.transaction.annotation.Transactional;

public interface UserService{
    Tokens signIn(SignInRequest request);
    Tokens signUp(SignUpRequest request);
    ProfileResponse getProfile(User user);
    void modifyProfile(User user, UpdateProfileRequest request);
}
