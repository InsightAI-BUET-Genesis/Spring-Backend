package com.example.InsightAI.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Incremental integer ID

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String topic;

    @Column(name = "resource_id", nullable = false)
    private String resourceId;

    @Column(nullable = false)
    private String knowledgeLevel;

    @Column(name = "note", nullable = false, length = 50000)
    private String note;  // Store JSON as a string

}
