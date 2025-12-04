package com.gavinbelanger.myumrp.controller;


import com.gavinbelanger.myumrp.model.Rating;
import com.gavinbelanger.myumrp.model.Media;
import com.gavinbelanger.myumrp.model.User;
import com.gavinbelanger.myumrp.service.RatingService;
import com.gavinbelanger.myumrp.repository.MediaRepository;
import com.gavinbelanger.myumrp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MediaRepository mediaRepository;

    // Add a rating
    @PostMapping("/add")
    public Rating addRating(@RequestParam String username,
                            @RequestParam Long mediaId,
                            @RequestParam double score) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Media media = mediaRepository.findById(mediaId).orElseThrow();
        return ratingService.addRating(user, media, score);
    }

    // Get average rating
    @GetMapping("/{mediaId}")
    public double getAverageRating(@PathVariable Long mediaId) {
        Media media = mediaRepository.findById(mediaId).orElseThrow();
        return ratingService.getAverageRating(media);
    }
}

