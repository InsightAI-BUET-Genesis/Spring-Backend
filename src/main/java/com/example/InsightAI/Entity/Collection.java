package com.example.InsightAI.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "collections")
public class Collection {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "count", nullable = false)
    private Integer count = 0;

    @Column(name = "original_name", nullable = true)
    private String originalName;

    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}
