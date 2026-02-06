package com.gavinbelanger.myumrp.controller;

import com.gavinbelanger.myumrp.model.Media;
import com.gavinbelanger.myumrp.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/movie")
    public Mono<String> searchMovie(@RequestParam String query) {
        return searchService.searchMovies(query);
    }

    @GetMapping("/tv")
    public Mono<String> searchTv(@RequestParam String query) {
        return searchService.searchTv(query);
    }

    @GetMapping("/game")
    public Mono<String> searchGame(@RequestParam String query) {
        return searchService.searchGames(query);
    }

    @GetMapping("/movie/save")
    public ResponseEntity<List<Media>> searchAndSaveMovie(@RequestParam String query) {
        List<Media> results = searchService.searchAndSaveMovies(query);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/tv/save")
    public ResponseEntity<List<Media>> searchAndSaveTv(@RequestParam String query) {
        List<Media> results = searchService.searchAndSaveTv(query);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/game/save")
    public ResponseEntity<List<Media>> searchAndSaveGame(@RequestParam String query) {
        List<Media> results = searchService.searchAndSaveGames(query);
        return ResponseEntity.ok(results);
    }
}