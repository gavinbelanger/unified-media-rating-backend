package com.gavinbelanger.myumrp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_list_items",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_list_id", "media_id"}))
public class UserListItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_list_id", nullable = false)
    private UserList userList;

    @ManyToOne
    @JoinColumn(name = "media_id", nullable = false)
    private Media media;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private LocalDateTime addedAt = LocalDateTime.now();
}
