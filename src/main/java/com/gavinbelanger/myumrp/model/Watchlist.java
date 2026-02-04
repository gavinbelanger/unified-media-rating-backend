package com.gavinbelanger.myumrp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "watchlist")
@NoArgsConstructor
@AllArgsConstructor
public class Watchlist {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;  // who added it to their watchlist

        @ManyToOne
        @JoinColumn(name = "media_id", nullable = false)
        private Media media;  // what media they want to watch

        private LocalDateTime addedAt = LocalDateTime.now();  // when they added it
}

