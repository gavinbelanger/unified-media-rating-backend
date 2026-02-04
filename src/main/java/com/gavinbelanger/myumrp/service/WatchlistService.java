package com.gavinbelanger.myumrp.service;

import com.gavinbelanger.myumrp.model.Media;
import com.gavinbelanger.myumrp.model.User;
import com.gavinbelanger.myumrp.model.Watchlist;
import com.gavinbelanger.myumrp.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WatchlistService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    public Watchlist addToWatchlist(User user, Media media) {
        Optional<Watchlist> existing = watchlistRepository.findByUserAndMedia(user, media);

        if (existing.isPresent()) {
            return existing.get();
        }

        Watchlist watchlistItem = new Watchlist();
        watchlistItem.setUser(user);
        watchlistItem.setMedia(media);

        return watchlistRepository.save(watchlistItem);
    }

    @Transactional
    public void removeFromWatchlist(User user, Media media) {
        watchlistRepository.deleteByUserAndMedia(user, media);
    }

    public List<Watchlist> getUserWatchlist(User user) {
        return watchlistRepository.findByUser(user);
    }

    public boolean isInWatchlist(User user, Media media) {
        return watchlistRepository.findByUserAndMedia(user, media).isPresent();
    }
}