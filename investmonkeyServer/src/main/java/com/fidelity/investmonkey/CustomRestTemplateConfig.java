package com.fidelity.investmonkey;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomRestTemplateConfig {

 

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Customize the RestTemplate instance if needed
        return builder
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
    }
}
