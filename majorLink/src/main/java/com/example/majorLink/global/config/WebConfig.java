package com.example.majorLink.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("**/")
                .allowedOrigins("http://localhost:3000")  // 허용할 도메인
                .allowedOrigins("http://localhost:3008")
                .allowedOrigins("http://localhost:3007") //프론트 테스트 url
                .allowedMethods("POST", "GET", "DELETE", "PUT")
                .allowedHeaders("*");
    }
}
