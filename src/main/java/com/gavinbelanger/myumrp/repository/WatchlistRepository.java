package com.gavinbelanger.myumrp.repository;

import com.gavinbelanger.myumrp.model.Media;
import com.gavinbelanger.myumrp.model.User;
import com.gavinbelanger.myumrp.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

    // Find all watchlist items for a specific user
    List<Watchlist> findByUser(User user);

    // Check if a user already has this media in their watchlist
    Optional<Watchlist> findByUserAndMedia(User user, Media media);

    // Delete a specific watchlist item
    void deleteByUserAndMedia(User user, Media media);
}
