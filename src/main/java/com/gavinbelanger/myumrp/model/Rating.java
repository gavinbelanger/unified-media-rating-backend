package com.gavinbelanger.myumrp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "ratings",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "media_id"}))
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

    @Min(1)
    @Max(10)
    private double score;
}

