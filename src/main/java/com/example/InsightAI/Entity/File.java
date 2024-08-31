package com.example.InsightAI.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "files")
public class File {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "collection_id", nullable = false)
    private String collectionId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "youtube_link", nullable = true)
    private String youtubeUrl;

    @Column(name = "collection_name", nullable = true)
    private String collectionName;

    @Column(name = "status", nullable = true)
    private String status = "Pending";

    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}
