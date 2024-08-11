package com.example.majorLink.service;

import com.example.majorLink.domain.User;
import com.example.majorLink.dto.request.SignInRequest;
import com.example.majorLink.dto.request.SignUpRequest;
import com.example.majorLink.dto.request.UpdateMyPageRequest;
import com.example.majorLink.dto.response.MyPageResponse;
import com.example.majorLink.global.auth.Tokens;

public interface UserService{
    Tokens signIn(SignInRequest request);
    Tokens signUp(SignUpRequest request);
    MyPageResponse getMyPage(User user);
    void modifyMyPage(User user, UpdateMyPageRequest request);
}
