package com.example.majorLink.global.config;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IamportClientConfig {

    @Bean
    public IamportClient iamportClient(@Value ("${iamport.api_key}") String apiKey,
                                       @Value("${iamport.api_secret}") String apiSecret) {
        return new IamportClient(apiKey, apiSecret);
    }
}
