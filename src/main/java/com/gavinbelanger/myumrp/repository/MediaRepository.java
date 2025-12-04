package com.gavinbelanger.myumrp.repository;

import com.gavinbelanger.myumrp.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {
    // Optional custom queries can be added here later
}

