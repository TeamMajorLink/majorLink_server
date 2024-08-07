package com.example.majorLink.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("**/")
                .allowedOrigins("http://localhost:3004")
                .allowedOrigins("http://localhost:3005") //프론트 테스트 url
                .allowedMethods("POST", "GET", "DELETE", "PUT")
                .allowedHeaders("*");
    }
}
