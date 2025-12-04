package com.gavinbelanger.myumrp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TmdbService {

    private final WebClient webClient;
    private final String tmdbApiKey;

    public TmdbService(
            @Value("${tmdb.api.key}") String tmdbApiKey
    ) {
        this.tmdbApiKey = tmdbApiKey;
        this.webClient = WebClient.builder()
                .baseUrl("https://api.themoviedb.org/3")
                .build();
    }

    public Mono<String> searchMovie(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/movie")
                        .queryParam("api_key", tmdbApiKey)
                        .queryParam("query", query)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> seachTv(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/serach/tv")
                        .queryParam("api_key", tmdbApiKey)
                        .queryParam("query", query)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
