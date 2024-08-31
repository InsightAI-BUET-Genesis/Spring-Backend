package com.example.InsightAI.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "image_url", nullable = true)
    private String imageUrl;

    @Column(name = "tasks", nullable = true, length = 50000)
    private String tasks;  // Store JSON as a string

    @Column(name = "search_results", nullable = true, length = 50000)
    private String searchResults;  // Store JSON as a string

    @Column(name = "tags", nullable = true, length = 50000)
    private String tags;  // Store JSON as a string

    @Column(name = "theme", nullable = true)
    private String theme="#FFFFFF";  // Store JSON as a string

    @Column(name = "user_id", nullable = false)
    private String userId;

    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}
