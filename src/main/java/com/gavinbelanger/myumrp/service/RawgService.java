package com.gavinbelanger.myumrp.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RawgService {

    private final WebClient webClient;
    private final String rawgApiKey;

    public RawgService(@Value("${rawg.api.key}") String rawgApiKey) {
        this.rawgApiKey = rawgApiKey;
        this.webClient = WebClient.builder()
                .baseUrl("https://api.rawg.io/api")
                .build();
    }

    public Mono<String> searchGames(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/games")
                        .queryParam("api_key", rawgApiKey)
                        .queryParam("search", query)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getGameById(String gameId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/games/" + gameId)
                        .queryParam("api_key", rawgApiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
