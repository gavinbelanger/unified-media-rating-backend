package com.gavinbelanger.myumrp.service;

import com.gavinbelanger.myumrp.model.Media;
import com.gavinbelanger.myumrp.model.MediaType;
import com.gavinbelanger.myumrp.repository.MediaRepository;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MediaImportService {

    private final TmdbService tmdbService;
    private final RawgService rawgService;
    private final MediaRepository mediaRepository;

    public MediaImportService(TmdbService tmdbService, RawgService rawgService, MediaRepository mediaRepository) {
        this.tmdbService = tmdbService;
        this.rawgService = rawgService;
        this.mediaRepository = mediaRepository;
    }

    public Media importMovie(String apiId) {
        Optional<Media> existing = mediaRepository.findByApiIdAndMediaType(apiId, MediaType.MOVIE);
        if (existing.isPresent()) {
            return existing.get();
        }

        String json = tmdbService.getMovieById(apiId).block();
        JSONObject obj = new JSONObject(json);

        Media media = new Media();
        media.setApiId(apiId);
        media.setTitle(obj.getString("title"));
        media.setPosterUrl("https://image.tmdb.org/t/p/w500" + obj.optString("poster_path", null));
        media.setDescription(obj.optString("overview", null));
        media.setMediaType(MediaType.MOVIE);

        return mediaRepository.save(media);
    }

    public Media importTv(String apiId) {
        Optional<Media> existing = mediaRepository.findByApiIdAndMediaType(apiId, MediaType.TV);
        if (existing.isPresent()) {
            return existing.get();
        }

        String json = tmdbService.getTvById(apiId).block();
        JSONObject obj = new JSONObject(json);

        Media media = new Media();
        media.setApiId(apiId);
        media.setTitle(obj.getString("name"));
        media.setPosterUrl("https://image.tmdb.org/t/p/w500" + obj.optString("poster_path", null));
        media.setDescription(obj.optString("overview", null));
        media.setMediaType(MediaType.TV);

        return mediaRepository.save(media);
    }

    public Media importGame(String apiId) {
        Optional<Media> existing = mediaRepository.findByApiIdAndMediaType(apiId, MediaType.GAME);
        if (existing.isPresent()) {
            return existing.get();
        }

        String json = rawgService.getGameById(apiId).block();
        JSONObject obj = new JSONObject(json);

        Media media = new Media();
        media.setApiId(apiId);
        media.setTitle(obj.getString("name"));
        media.setPosterUrl(obj.optString("background_image", null));
        media.setDescription(obj.optString("description_raw", null));
        media.setMediaType(MediaType.GAME);

        return mediaRepository.save(media);
    }

    public Media importMedia(String type, String apiId) {
        switch (type.toLowerCase()) {
            case "movie": return importMovie(apiId);
            case "tv":    return importTv(apiId);
            case "game":  return importGame(apiId);
            default:
                throw new IllegalArgumentException("Unknown media type: " + type);
        }
    }
}
