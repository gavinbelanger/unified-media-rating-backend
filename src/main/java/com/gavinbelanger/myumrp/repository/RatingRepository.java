package com.gavinbelanger.myumrp.repository;

import com.gavinbelanger.myumrp.model.Rating;
import com.gavinbelanger.myumrp.model.Media;
import com.gavinbelanger.myumrp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByMedia(Media media);                // get all ratings for a media item
    Optional<Rating> findByUserAndMedia(User user, Media media);  // optional: user updates rating
}

