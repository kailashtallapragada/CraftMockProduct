package com.ktcraft.validationproduct.http.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class HttpClientConfig {

    @Bean
    public HttpClient createHttpClient() {
        return HttpClient.newHttpClient();
    }
}
