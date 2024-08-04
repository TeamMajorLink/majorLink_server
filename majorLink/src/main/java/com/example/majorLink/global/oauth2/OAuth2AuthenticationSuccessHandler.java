package com.example.majorLink.global.oauth2;

import com.example.majorLink.global.auth.AuthTokens;
import com.example.majorLink.global.auth.AuthTokensGenerator;
import com.example.majorLink.global.infra.naver.NaverLoginParams;
import com.example.majorLink.global.oauth2.OAuthLoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final OAuthLoginService oAuthLoginService;
    private final AuthTokensGenerator authTokensGenerator;

    public OAuth2AuthenticationSuccessHandler(OAuthLoginService oAuthLoginService, AuthTokensGenerator authTokensGenerator) {
        this.oAuthLoginService = oAuthLoginService;
        this.authTokensGenerator = authTokensGenerator;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String authorizationCode = request.getParameter("code");
        String state = request.getParameter("state");

        // NaverLoginParams 인스턴스 생성
        NaverLoginParams params = new NaverLoginParams();
        params.setAuthorizationCode(authorizationCode);
        params.setState(state);

        AuthTokens authTokens = oAuthLoginService.login(params);

        // 토큰을 클라이언트에게 반환
        response.addHeader("Authorization", "Bearer " + authTokens.getAccessToken());

        // 로그인 성공 후 리다이렉션 URL 설정
        String redirectUrl = UriComponentsBuilder.fromUriString("http://localhost:9000/naver/callback")
                .path("/home") // 로그인 후 이동할 페이지
                .queryParam("access_token", authTokens.getAccessToken())
                .build()
                .toUriString();

        response.sendRedirect(redirectUrl);


    }
}
