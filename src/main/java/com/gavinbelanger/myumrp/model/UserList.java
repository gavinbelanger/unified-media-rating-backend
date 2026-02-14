package com.gavinbelanger.myumrp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user_lists")
public class UserList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private ListType listType;

    private boolean isPublic = true;

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "userList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserListItem> items = new ArrayList<>();
}