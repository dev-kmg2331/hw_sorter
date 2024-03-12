package org.hw_sorter.hw_la_client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
@Slf4j @RequiredArgsConstructor
public class ReactiveClient {
    private final WebClient webClient;

    public String searchCharacter(String characterName) {
        return webClient
                .get()
                .uri("/get")
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(
                        Retry.fixedDelay(1, Duration.ofSeconds(3))
                                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> new RuntimeException())
                )
                .log()
                .doOnSuccess(log::info)
                .block();
    }
}
