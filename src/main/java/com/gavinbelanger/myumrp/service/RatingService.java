package com.gavinbelanger.myumrp.service;

import com.gavinbelanger.myumrp.model.Rating;
import com.gavinbelanger.myumrp.model.Media;
import com.gavinbelanger.myumrp.model.User;
import com.gavinbelanger.myumrp.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public Rating addOrUpdateRating(User user, Media media, double score) {
        // Check if user already rated this media
        Optional<Rating> existingRating = ratingRepository.findByUserAndMedia(user, media);

        if (existingRating.isPresent()) {
            // Update existing rating
            Rating rating = existingRating.get();
            rating.setScore(score);
            return ratingRepository.save(rating);
        } else {
            // Create new rating
            Rating newRating = new Rating(null, user, media, score);
            return ratingRepository.save(newRating);
        }
    }

    public double getAverageRating(Media media) {
        List<Rating> ratings = ratingRepository.findByMedia(media);
        if (ratings.isEmpty()) return 0.0;

        double sum = 0;
        for (Rating r : ratings) sum += r.getScore();
        return sum / ratings.size();
    }

    public Optional<Rating> getUserRatingForMedia(User user, Media media) {
        return ratingRepository.findByUserAndMedia(user, media);
    }

    public void deleteRating(User user, Media media) {
        Optional<Rating> rating = ratingRepository.findByUserAndMedia(user, media);
        rating.ifPresent(r -> ratingRepository.delete(r));
    }
}