package com.gavinbelanger.myumrp.controller;


import com.gavinbelanger.myumrp.model.Rating;
import com.gavinbelanger.myumrp.model.Media;
import com.gavinbelanger.myumrp.model.User;
import com.gavinbelanger.myumrp.service.RatingService;
import com.gavinbelanger.myumrp.repository.MediaRepository;
import com.gavinbelanger.myumrp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addOrUpdateRating(
            @RequestParam String username,
            @RequestParam Long mediaId,
            @RequestParam double score) {

        if (score < 1 || score > 10) {
            return ResponseEntity.badRequest().body("Score must be between 1 and 10");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        Rating rating = ratingService.addOrUpdateRating(user, media, score);
        return ResponseEntity.ok(rating);
    }

    @GetMapping("/media/{mediaId}/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long mediaId) {
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media not found"));
        double average = ratingService.getAverageRating(media);
        return ResponseEntity.ok(average);
    }

    @GetMapping("/user/{username}/media/{mediaId}")
    public ResponseEntity<?> getUserRating(
            @PathVariable String username,
            @PathVariable Long mediaId) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        Optional<Rating> rating = ratingService.getUserRatingForMedia(user, media);
        return rating.isPresent()
                ? ResponseEntity.ok(rating.get())
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRating(
            @RequestParam String username,
            @RequestParam Long mediaId) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        ratingService.deleteRating(user, media);
        return ResponseEntity.ok("Rating deleted successfully");
    }
}