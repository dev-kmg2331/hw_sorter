package org.hw_sorter.hw_la_client.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {
    private final Environment environment;

    @Bean
    public WebClient init() {
        return WebClient.builder()
                .defaultHeader("x-auth-token", environment.getProperty("la-client.api-key"))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl("https://baseUrl")
                .build();
    }
}
