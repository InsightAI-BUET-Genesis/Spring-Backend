package com.example.InsightAI.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "questions_sets")
public class QuestionSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Incremental integer ID

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String type;

    @Column(name = "question_count", nullable = false)
    private int questionCount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "resource_id", nullable = false)
    private String resourceId;

    @Column(name = "highScore", nullable = true)
    private Integer highScore = 0;

    @Column(name = "questions", nullable = false, length = 50000)
    private String questions;  // Store JSON as a string

    @Column(name = "topic_type", nullable = true)
    private String topicType;

    @Column(name = "question_type", nullable = true)
    private String questionType;

    @Column(name = "answer_type", nullable = true)
    private String answerType;

    @Column(name = "difficulty_level", nullable = true)
    private String difficultyLevel;
}
