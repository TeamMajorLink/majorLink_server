package com.example.majorLink.service;

import com.example.majorLink.domain.User;
import com.example.majorLink.dto.request.ProfileCardRequest;
import com.example.majorLink.dto.response.ProfileCardResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ProfileCardService {
    void createProfileCard(User user, ProfileCardRequest request);
    void modifyProfileCard(User user, ProfileCardRequest request);
    ProfileCardResponse getProfileCard(User user, String nickname);
}
