package com.example.InsightAI.Interface;

import com.example.InsightAI.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // Method to find a user by their email
    Optional<User> findByEmail(String email);

    // Method to find a user by their userId
    Optional<User> findByUserId(String userId);
}
