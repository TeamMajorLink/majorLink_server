package com.example.majorLink.global.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SocialService {
    private final RestClient restClient = RestClient.create();
    private static final String AUTH_TYPE = "Bearer ";
    public void loadUserInfo(String provider, String providerId, String accessToken) {
        // TODO: ACCESS TOKEN 복호화

        String header = AUTH_TYPE + accessToken;
        String id = "";

        if(provider.equals("NAVER")) {
            Map result = restClient.get()
                    .uri("https://openapi.naver.com/v1/nid/me")
                    .header("Authorization", header)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                        try {
                            throw new Exception("에러");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }))
                    .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                        try {
                            throw new Exception("에러");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }))
                    .body(Map.class);

            Map<String, String> response = new HashMap<>((LinkedHashMap) result.get("response"));
            id = response.get("id");
        }
//        } else if(provider.equals("GOOGLE")) {
//            log.info("Google start");
//            Map result = restClient.get()
//                    .uri("https://www.googleapis.com/oauth2/v2/userinfo")
//                    .header("Authorization", header)
//                    .retrieve()
//                    .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
//                        throw new GeneralException(Code.OAUTH_NOT_FOUND_TOKEN);
//                    }))
//                    .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
//                        throw new GeneralException(Code.OAUTH_FIND_ERROR);
//                    }))
//                    .body(Map.class);
//
//            id = String.valueOf(result.get("id"));
//        } else {
//            Map result = restClient.get()
//                    .uri("https://kapi.kakao.com/v2/user/me")
//                    .header("Authorization", header)
//                    .retrieve()
//                    .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
//                        throw new GeneralException(Code.OAUTH_NOT_FOUND_TOKEN);
//                    }))
//                    .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
//                        throw new GeneralException(Code.OAUTH_FIND_ERROR);
//                    }))
//                    .body(Map.class);
//
//            id = String.valueOf(result.get("id"));
//        }

        if(!providerId.equals(id)) {
            throw new RuntimeException("Provider Id와 일치하지 않는 소셜 id");
        }
    }
}
