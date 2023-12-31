package com.ktcraft.validationproduct.service.circuitbreaker.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CircuitBreakerConfiguration {

    @Bean
    public CircuitBreakerConfig getCircuitBreakerConfig() {
        return CircuitBreakerConfig.ofDefaults();
    }
}
