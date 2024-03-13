package com.example.Surisuri_Masuri.config;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PortoneConfig {

    @Value("${imp.imp_secret}")
    private String apiSecret;

    @Value("${imp.imp_key}")
    private String apiKey;

    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(apiKey, apiSecret);
    }
}
