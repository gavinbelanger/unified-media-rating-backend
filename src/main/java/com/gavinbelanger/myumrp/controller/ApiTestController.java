package com.gavinbelanger.myumrp.controller;

import com.gavinbelanger.myumrp.service.TmdbService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/test")
public class ApiTestController {

    private final TmdbService tmdbService;

    public ApiTestController(TmdbService tmdbService) {
        this.tmdbService = tmdbService;
    }

    @GetMapping("/tmdb")
    public Mono<String> testTmdb(@RequestParam String query) {
        return tmdbService.searchMovie(query);
    }
}
