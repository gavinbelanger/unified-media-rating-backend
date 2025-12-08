package com.gavinbelanger.myumrp.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SearchService {

    private final TmdbService tmdbService;
    private final RawgService rawgService;

    public SearchService(TmdbService tmdbService, RawgService rawgService) {
        this.tmdbService = tmdbService;
        this.rawgService = rawgService;
    }

    public Mono<String> searchMovies(String query) {
        return tmdbService.searchMovie(query);
    }

    public Mono<String> searchTv(String query) {
        return tmdbService.searchTv(query);
    }

    public Mono<String> searchGames(String query) {
        return rawgService.searchGames(query);
    }
}

