package com.example.majorLink.global.oauth2;

import com.example.majorLink.domain.User;
import com.example.majorLink.global.auth.AuthTokens;
import com.example.majorLink.global.auth.AuthTokensGenerator;
import com.example.majorLink.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final UserRepository userRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        UUID userId = findOrCreateMember(oAuthInfoResponse);
        return authTokensGenerator.generate(userId);
    }

    private UUID findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return userRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(User::getId)
                .orElseGet(() -> newUser(oAuthInfoResponse));
    }

    private UUID newUser(OAuthInfoResponse oAuthInfoResponse) {
        User user = User.builder()
                .email(oAuthInfoResponse.getEmail())
                .username(oAuthInfoResponse.getUsername())
//                .oAuthProvider(oAuthInfoResponse.getSocialType())
                .build();

        return userRepository.save(user).getId();
    }
}