package com.gavinbelanger.myumrp.service;

import com.gavinbelanger.myumrp.model.Rating;
import com.gavinbelanger.myumrp.model.Media;
import com.gavinbelanger.myumrp.model.User;
import com.gavinbelanger.myumrp.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public Rating addRating(User user, Media media, double score) {
        // Optional: check if user already rated
        return ratingRepository.save(new Rating(null, user, media, score));
    }

    public double getAverageRating(Media media) {
        List<Rating> ratings = ratingRepository.findByMedia(media);
        if (ratings.isEmpty()) return 0.0;

        double sum = 0;
        for (Rating r : ratings) sum += r.getScore();
        return sum / ratings.size();
    }
}

