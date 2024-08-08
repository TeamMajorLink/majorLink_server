package com.example.majorLink.global.oauth2;

import com.example.majorLink.domain.Social;
import com.example.majorLink.domain.enums.SocialStatus;
//import com.example.majorLink.global.auth.AuthTokensGenerator;
import com.example.majorLink.global.auth.Tokens;
import com.example.majorLink.global.jwt.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    //    private final AuthTokensGenerator authTokensGenerator;
    private final OAuthLoginService oAuthService;
    private final JwtService jwtService;
    @Value("${default.login.redirect.url}")
    private String redirectUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(authentication.getPrincipal() instanceof CustomOAuth2User oAuth2User) {
            Social socialInfo = oAuth2User.getSocialInfo();

            response.setCharacterEncoding("utf-8");

            if(socialInfo.getSocialStatus() == SocialStatus.CONNECTED){
                String userUUID = String.valueOf(socialInfo.getUser().getId());

                Tokens tokens = jwtService.createAndSaveTokens(userUUID);

                String uri = UriComponentsBuilder.fromUriString(redirectUrl + "/member")
                        .queryParam("X-Auth-Token", tokens.getAccessToken())
                        .queryParam("refresh-Token", tokens.getRefreshToken())
                        .toUriString();

                response.sendRedirect(uri);
                return;
            }

            OAuthAttributes attributes = oAuth2User.getOAuthAttributes();
            String uri = UriComponentsBuilder.fromUriString(redirectUrl + "/new-user")
//                    .queryParam("temp-token", String.valueOf(socialInfo.getTemporalToken()))
                    .queryParam("username", attributes.getUsername())
                    .queryParam("email", attributes.getEmail())
                    .queryParam("profileImg", attributes.getProfileImg())
                    .queryParam("phone", attributes.getPhone())
                    .queryParam("gender", attributes.getGender())
                    .toUriString();

            response.sendRedirect(uri);
        }
    }

}
