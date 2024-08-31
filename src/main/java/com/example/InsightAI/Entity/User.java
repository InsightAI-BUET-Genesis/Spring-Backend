package com.example.InsightAI.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_table")
public class User {

    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "profile_picture_url", nullable = false)
    private String profilePictureUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTSLU5_eUUGBfxfxRd4IquPiEwLbt4E_6RYMw&s";

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "educational_institute", nullable = false)
    private String educationalInstitute = "Not Provided";

    @Column(name = "current_academic_year", nullable = false)
    private String currentAcademicYear = "Not Provided";

    @Column(name = "user_bio", nullable = false, length = 5000)
    private String userBio = "bio is empty";

    @Column(name = "academic_subject", nullable = false)
    private String academicSubject = "Not Provided";

//    @Column(name = "created_at", nullable = false, updatable = false)
//    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (this.userId == null) {
            this.userId = UUID.randomUUID().toString();
        }
//        this.createdAt = LocalDateTime.now(); // Automatically set the creation time
    }

    // Constructor with parameters
    public User(String userId, String userName, String email) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
    }
}
