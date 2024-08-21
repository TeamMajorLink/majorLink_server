package com.example.majorLink.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000",
                        "http://localhost:3007","http://localhost:3008",
                        "http://localhost:59923",
                        "http://localhost:3003",
                        "http://localhost:3004"
                ) //프론트 테스트 url입니다.
                .allowedMethods("POST", "GET", "DELETE", "PUT", "PATCH", "OPTIONS")
                .allowedHeaders("Content-Type", "Authorization","x-auth-token")
                .allowCredentials(true);
    }
}
