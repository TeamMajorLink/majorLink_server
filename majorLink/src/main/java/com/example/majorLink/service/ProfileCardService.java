package com.example.majorLink.service;

import com.example.majorLink.domain.User;
import com.example.majorLink.dto.request.CreateProfileCardRequest;
import org.springframework.transaction.annotation.Transactional;

public interface ProfileCardService {
    void createProfileCard(User user, CreateProfileCardRequest request);
}
