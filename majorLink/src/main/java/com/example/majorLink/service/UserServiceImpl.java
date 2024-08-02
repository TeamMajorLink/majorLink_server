package com.example.majorLink.service;

import com.example.majorLink.domain.Social;
import com.example.majorLink.domain.User;
import com.example.majorLink.domain.enums.Gender;
import com.example.majorLink.domain.enums.LearnPart;
import com.example.majorLink.domain.enums.Role;
import com.example.majorLink.domain.enums.SocialType;
import com.example.majorLink.dto.request.SignUpRequest;
import com.example.majorLink.repository.SocialRepository;
import com.example.majorLink.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final SocialRepository socialRepository;

}
