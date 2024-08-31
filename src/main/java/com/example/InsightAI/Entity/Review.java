package com.example.InsightAI.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "review_table")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false, unique = true)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "user_comment", nullable = false, length = 5000)
    private String userComment;

    @Column(name = "review_sentiment", nullable = false)
    private String reviewSentiment;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(); // Automatically set the creation time
    }

    public Review(User user, String productId, String userComment, String reviewSentiment) {
        this.user = user;
        this.productId = productId;
        this.userComment = userComment;
        this.reviewSentiment = reviewSentiment;
    }
}
