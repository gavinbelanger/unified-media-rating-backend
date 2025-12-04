package com.gavinbelanger.myumrp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "ratings")
@NoArgsConstructor   // required by JPA
@AllArgsConstructor  // allows constructor with fields
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // who rated

    @ManyToOne
    @JoinColumn(name = "media_id", nullable = false)
    private Media media;  // media being rated

    private double score;  // 1-10 rating
}

