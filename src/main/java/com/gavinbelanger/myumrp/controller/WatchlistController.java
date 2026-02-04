package com.gavinbelanger.myumrp.controller;

import com.gavinbelanger.myumrp.model.Media;
import com.gavinbelanger.myumrp.model.User;
import com.gavinbelanger.myumrp.model.Watchlist;
import com.gavinbelanger.myumrp.repository.MediaRepository;
import com.gavinbelanger.myumrp.repository.UserRepository;
import com.gavinbelanger.myumrp.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {

    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @PostMapping("/add")
    public ResponseEntity<Watchlist> addToWatchlist(
            @RequestParam String username,
            @RequestParam Long mediaId) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media not found: " + mediaId));

        Watchlist watchlistItem = watchlistService.addToWatchlist(user, media);

        return ResponseEntity.ok(watchlistItem);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromWatchlist(
            @RequestParam String username,
            @RequestParam Long mediaId) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media not found: " + mediaId));

        watchlistService.removeFromWatchlist(user, media);

        return ResponseEntity.ok("Removed from watchlist");
    }

    @GetMapping
    public ResponseEntity<List<Watchlist>> getWatchlist(@RequestParam String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        List<Watchlist> watchlist = watchlistService.getUserWatchlist(user);

        return ResponseEntity.ok(watchlist);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkWatchlist(
            @RequestParam String username,
            @RequestParam Long mediaId) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media not found: " + mediaId));

        boolean inWatchlist = watchlistService.isInWatchlist(user, media);

        return ResponseEntity.ok(inWatchlist);
    }
}