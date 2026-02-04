package com.gavinbelanger.myumrp.repository;

import com.gavinbelanger.myumrp.model.Media;
import com.gavinbelanger.myumrp.model.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Long> {
    // Optional custom queries can be added here later
    Optional<Media> findByApiId(String apiId);
    Optional<Media> findByApiIdAndMediaType(String apiId, MediaType type);
}

