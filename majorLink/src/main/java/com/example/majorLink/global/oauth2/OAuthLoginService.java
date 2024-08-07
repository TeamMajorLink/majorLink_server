package com.example.majorLink.global.oauth2;

import com.example.majorLink.domain.Social;
import com.example.majorLink.domain.enums.*;
//import com.example.majorLink.global.auth.AuthTokensGenerator;
import com.example.majorLink.repository.SocialRepository;
import com.example.majorLink.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuthLoginService extends DefaultOAuth2UserService {
    private static final Logger logger = LoggerFactory.getLogger(OAuthLoginService.class);
    private final UserRepository userRepository;
    private final SocialRepository socialRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("***********************");
        log.info(userRequest.getAccessToken().getTokenValue());
        log.info("***********************");


        SocialType provider = SocialType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
        String userNameAttribute = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes oAuthAttributes = OAuthAttributes.of(provider, userNameAttribute, oAuth2User.getAttributes());

        log.info(oAuthAttributes.getId());
        log.info("***********************");
        Optional<Social> social = socialRepository.findBySocialTypeAndProviderId(provider,oAuthAttributes.getId());

        Social info;

        if (social.isEmpty()) {
            info = socialRepository.save(Social.builder()
                    .socialType(provider)
                    .providerId(oAuthAttributes.getId())
                    .socialStatus(SocialStatus.WAITING_SIGN_UP)
                    .build()
            );

        } else {
            info = social.get();

        }

        return CustomOAuth2User.builder()
                .delegate(oAuth2User)
                .oAuthAttributes(oAuthAttributes)
                .socialInfo(info)
                .build();
    }

//    public AuthTokens login(OAuthLoginParams params) {
//        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
//        UUID userId = findOrCreateMember(oAuthInfoResponse);
//        return authTokensGenerator.generate(userId);
//    }
//
//    public AuthTokens signUp(SignUpRequest signUpRequest) {
//        UUID userId = createNewUser(signUpRequest);
//        return authTokensGenerator.generate(userId);
//    }
//
//    public AuthTokens signIn(SignInRequest signInRequest) {
//        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(signInRequest.getProvider(), signInRequest.getAccessToken());
//        UUID userId = findOrCreateMember(oAuthInfoResponse);
//        return authTokensGenerator.generate(userId);
//    }



}