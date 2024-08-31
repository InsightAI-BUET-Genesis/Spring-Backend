package com.example.InsightAI.Service;

import com.example.InsightAI.Entity.User;
import com.example.InsightAI.Interface.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/users")
public class UserService {

    @Autowired
    private UserRepository userRepo;

    // Endpoint for registering a new user
    @PostMapping("/register")
    public ResponseEntity<String> addUser(@RequestParam Map<String, String> formData) {
        String userId = formData.get("userId");
        String userName = formData.get("userName");
        String email = formData.get("email");

        // Check if a user with the same email already exists
        Optional<User> existingUser = userRepo.findById(userId);
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User with this ID already exists");
        }

        // Create and save the new user
        User user = new User(userId, userName, email);
        userRepo.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User Registered successfully");
    }

    // Endpoint for fetching a user by userId
    @GetMapping("/get-user/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {

        Optional<User> user = userRepo.findByUserId(userId);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get()); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }

    // Endpoint for updating a user
    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody Map<String, String> formData) {
        Optional<User> existingUser = userRepo.findByUserId(userId);

        System.out.println(userId);
        System.out.println(formData.get("userName"));

        if (existingUser.isPresent()) {
            User user = existingUser.get();

            // Update user fields with new values if they exist in the request
            if (formData.containsKey("userName")) {
                user.setUserName(formData.get("userName"));
            }

            if (formData.containsKey("profilePictureUrl")) {
                user.setProfilePictureUrl(formData.get("profilePictureUrl"));
            }
            if (formData.containsKey("educationalInstitute")) {
                user.setEducationalInstitute(formData.get("educationalInstitute"));
            }
            if (formData.containsKey("currentAcademicYear")) {
                user.setCurrentAcademicYear(formData.get("currentAcademicYear"));
            }
            if (formData.containsKey("userBio")) {
                user.setUserBio(formData.get("userBio"));
            }
            if (formData.containsKey("academicSubject")) {
                user.setAcademicSubject(formData.get("academicSubject"));
            }

            // Save the updated user back to the repository
            userRepo.save(user);

            return ResponseEntity.ok("User updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
    }
}
