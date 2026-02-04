package com.gavinbelanger.myumrp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apiId;  // TMDB/RAWG ID
    private String title;
    private String posterUrl;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    @Column(columnDefinition = "TEXT")
    private String description;
}

