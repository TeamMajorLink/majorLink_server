package com.example.majorLink.service;

import com.example.majorLink.domain.Social;
import com.example.majorLink.domain.User;
import com.example.majorLink.domain.enums.*;
import com.example.majorLink.dto.request.SignInRequest;
import com.example.majorLink.dto.request.SignUpRequest;
import com.example.majorLink.dto.response.ProfileResponse;
import com.example.majorLink.global.auth.AuthTokensGenerator;
import com.example.majorLink.global.auth.Tokens;
import com.example.majorLink.global.jwt.JwtConfig;
import com.example.majorLink.global.jwt.JwtService;
import com.example.majorLink.global.auth.service.RedisService;
import com.example.majorLink.global.auth.service.SocialService;
import com.example.majorLink.repository.SocialRepository;
import com.example.majorLink.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SocialRepository socialRepository;
    private final SocialService socialService;
    private final RedisService redisService;
    private final JwtService jwtService;
    private final AuthTokensGenerator authTokensGenerator;

    @Override
    public Tokens signIn(SignInRequest request) {
        socialService.loadUserInfo(request.getProvider(), request.getProviderId(), request.getAccessToken());
        Social social = socialRepository.findBySocialTypeAndProviderId(SocialType.valueOf(request.getProvider()), request.getProviderId())
                .orElseThrow(() -> new RuntimeException("소셜 로그인 정보가 없습니다."));
        User user = social.getUser();

        if (user == null) {
            throw new RuntimeException("회원 가입이 되지 않은 회원입니다");
        }

        Tokens tokens = jwtService.createToken(String.valueOf(user.getId()));
        redisService.setValuesWithTimeout(tokens.getRefreshToken(), String.valueOf(user.getId()), JwtConfig.REFRESH_TOKEN_VALID_TIME);

        return tokens;
    }

    @Override
    public Tokens signUp(SignUpRequest signUpRequest) {
        socialService.loadUserInfo(signUpRequest.getProvider(), signUpRequest.getProviderId(), signUpRequest.getAccessToken());

        SocialType socialType = SocialType.valueOf(signUpRequest.getProvider());
        Optional<Social> existingSocial = socialRepository.findBySocialTypeAndProviderId(socialType, signUpRequest.getProviderId());

        User user = null;
        if (existingSocial.isPresent()) {
            Social social = existingSocial.get();
            if (social.getSocialStatus() == SocialStatus.CONNECTED) {
                throw new RuntimeException("이미 가입된 계정이 존재합니다.");
            } else if (social.getSocialStatus() == SocialStatus.WAITING_SIGN_UP) {
                Gender gender = Gender.valueOf(signUpRequest.getGender().toUpperCase());
                LearnPart learnPart = LearnPart.valueOf(signUpRequest.getLearnPart().toUpperCase());

                user = User.builder()
                        .id(UUID.randomUUID())
                        .email(signUpRequest.getEmail())
                        .username(signUpRequest.getUsername())
                        .gender(gender)
                        .phone(signUpRequest.getPhone())
                        .profileImage(signUpRequest.getProfileImg())
                        .firstMajor(signUpRequest.getFirstMajor())
                        .secondMajor(signUpRequest.getSecondMajor())
                        .favorite(signUpRequest.getFavorite())
                        .role(Role.valueOf(signUpRequest.getRole()))
                        .learnPart(learnPart)
                        .build();

                user = userRepository.save(user);

                log.info("User created with ID: " + user.getId());

                // Create a new Social entity
                social.updateSocialStatus(SocialStatus.CONNECTED);
                social.updateUser(user);

                socialRepository.save(social);
            } else {
                throw new RuntimeException("Invalid social status.");
            }
        }

        Tokens tokens = jwtService.createToken(String.valueOf(user.getId()));

        return tokens;
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileResponse getProfile(User user) {
        return ProfileResponse.builder()
                .profileImg(user.getProfileImage())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstMajor(user.getFirstMajor())
                .secondMajor(user.getSecondMajor())
                .favorite(user.getFavorite())
                .gender(user.getGender())
                .build();
    }
}
