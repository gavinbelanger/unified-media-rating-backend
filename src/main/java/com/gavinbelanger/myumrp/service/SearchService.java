package com.gavinbelanger.myumrp.service;

import com.gavinbelanger.myumrp.model.Media;
import com.gavinbelanger.myumrp.model.MediaType;
import com.gavinbelanger.myumrp.repository.MediaRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SearchService {

    private final TmdbService tmdbService;
    private final RawgService rawgService;
    private final MediaRepository mediaRepository;

    public SearchService(TmdbService tmdbService, RawgService rawgService, MediaRepository mediaRepository) {
        this.tmdbService = tmdbService;
        this.rawgService = rawgService;
        this.mediaRepository = mediaRepository;
    }

    public Mono<String> searchMovies(String query) {
        return tmdbService.searchMovie(query);
    }

    public Mono<String> searchTv(String query) {
        return tmdbService.searchTv(query);
    }

    public Mono<String> searchGames(String query) {
        return rawgService.searchGames(query);
    }

    public List<Media> searchAndSaveMovies(String query) {
        // Get search results from TMDB
        String jsonResponse = tmdbService.searchMovie(query).block();
        JSONObject json = new JSONObject(jsonResponse);
        JSONArray results = json.getJSONArray("results");

        List<Media> savedMedia = new ArrayList<>();

        // Loop through first 10 results
        int limit = Math.min(results.length(), 10);
        for (int i = 0; i < limit; i++) {
            JSONObject movieJson = results.getJSONObject(i);

            String apiId = String.valueOf(movieJson.getInt("id"));

            // Check if already in database
            Optional<Media> existing = mediaRepository.findByApiIdAndMediaType(apiId, MediaType.MOVIE);

            if (existing.isPresent()) {
                // Already saved, just return it
                savedMedia.add(existing.get());
            } else {
                // Not in database, save it
                Media media = new Media();
                media.setApiId(apiId);
                media.setTitle(movieJson.getString("title"));
                media.setPosterUrl("https://image.tmdb.org/t/p/w500" + movieJson.optString("poster_path", ""));
                media.setDescription(movieJson.optString("overview", "No description available"));
                media.setMediaType(MediaType.MOVIE);

                Media saved = mediaRepository.save(media);
                savedMedia.add(saved);
            }
        }

        return savedMedia;
    }

    /**
     * NEW: Search for TV shows AND auto-save to database
     */
    public List<Media> searchAndSaveTv(String query) {
        String jsonResponse = tmdbService.searchTv(query).block();
        JSONObject json = new JSONObject(jsonResponse);
        JSONArray results = json.getJSONArray("results");

        List<Media> savedMedia = new ArrayList<>();

        int limit = Math.min(results.length(), 10);
        for (int i = 0; i < limit; i++) {
            JSONObject tvJson = results.getJSONObject(i);

            String apiId = String.valueOf(tvJson.getInt("id"));

            Optional<Media> existing = mediaRepository.findByApiIdAndMediaType(apiId, MediaType.TV);

            if (existing.isPresent()) {
                savedMedia.add(existing.get());
            } else {
                Media media = new Media();
                media.setApiId(apiId);
                media.setTitle(tvJson.getString("name"));
                media.setPosterUrl("https://image.tmdb.org/t/p/w500" + tvJson.optString("poster_path", ""));
                media.setDescription(tvJson.optString("overview", "No description available"));
                media.setMediaType(MediaType.TV);

                Media saved = mediaRepository.save(media);
                savedMedia.add(saved);
            }
        }

        return savedMedia;
    }

    /**
     * NEW: Search for games AND auto-save to database
     */
    public List<Media> searchAndSaveGames(String query) {
        String jsonResponse = rawgService.searchGames(query).block();
        JSONObject json = new JSONObject(jsonResponse);
        JSONArray results = json.getJSONArray("results");

        List<Media> savedMedia = new ArrayList<>();

        int limit = Math.min(results.length(), 10);
        for (int i = 0; i < limit; i++) {
            JSONObject gameJson = results.getJSONObject(i);

            String apiId = String.valueOf(gameJson.getInt("id"));

            Optional<Media> existing = mediaRepository.findByApiIdAndMediaType(apiId, MediaType.GAME);

            if (existing.isPresent()) {
                savedMedia.add(existing.get());
            } else {
                Media media = new Media();
                media.setApiId(apiId);
                media.setTitle(gameJson.getString("name"));
                media.setPosterUrl(gameJson.optString("background_image", ""));
                media.setDescription(gameJson.optString("description", "No description available"));
                media.setMediaType(MediaType.GAME);

                Media saved = mediaRepository.save(media);
                savedMedia.add(saved);
            }
        }

        return savedMedia;
    }
}


