// package com.example.InsightAI.Service;

// import com.example.InsightAI.Entity.Review;
// import com.example.InsightAI.Entity.User;
// import com.example.InsightAI.Interface.ReviewRepository;
// import com.example.InsightAI.Interface.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.*;

// @CrossOrigin(origins = "*", allowedHeaders = "*")
// @RestController
// @RequestMapping("/reviews")
// public class ReviewService {

//     @Autowired
//     private ReviewRepository reviewRepo;

//     @Autowired
//     private UserRepository userRepo;

//     // Endpoint for posting a review
//     @PostMapping("/add")
//     public ResponseEntity<String> addReview(@RequestBody Map<String, String> formData) {
//         String userId = formData.get("user_id");
//         String productId = formData.get("product_id");
//         String userComment = formData.get("user_comment");
//         String reviewSentiment = formData.get("review_sentiment");

//         // Check if the user exists
//         Optional<User> userOptional = userRepo.findByUserId(userId);
//         if (userOptional.isEmpty()) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                     .body("User not found");
//         }

//         // Create and save the review
//         Review review = new Review(userOptional.get(), productId, userComment, reviewSentiment);
//         reviewRepo.save(review);

//         return ResponseEntity.status(HttpStatus.CREATED)
//                 .body("Review added successfully");
//     }

//     // Endpoint for fetching reviews by productId
//     @GetMapping("/get-reviews/{productId}")
//     public ResponseEntity<List<Map<String, Object>>> getReviewsByProductId(@PathVariable String productId) {
//         List<Review> reviews = reviewRepo.findByProductId(productId);

//         // Transform the reviews into the desired response format
//         List<Map<String, Object>> response = new ArrayList<>();
//         for (Review review : reviews) {
//             Map<String, Object> reviewData = new HashMap<>();
//             reviewData.put("review_id", review.getReviewId());
//             reviewData.put("product_id", review.getProductId());
//             reviewData.put("user_name", review.getUser().getUserName());
//             reviewData.put("profile_picture_url", review.getUser().getProfilePictureUrl());
//             reviewData.put("user_comment", review.getUserComment());
//             reviewData.put("review_sentiment", review.getReviewSentiment());
//             reviewData.put("created_at", review.getCreatedAt());

//             response.add(reviewData);
//         }

//         return ResponseEntity.ok(response);
//     }
// }
package com.example.InsightAI.Service;

import com.example.InsightAI.Entity.Review;
import com.example.InsightAI.Entity.User;
import com.example.InsightAI.Interface.ReviewRepository;
import com.example.InsightAI.Interface.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/reviews")
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private UserRepository userRepo;

    // Endpoint for posting a review
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addReview(@RequestBody Map<String, String> formData) {
        String userId = formData.get("user_id");
        String productId = formData.get("product_id");
        String userComment = formData.get("user_comment");
        String reviewSentiment = formData.get("review_sentiment");

        // Check if the user exists
        Optional<User> userOptional = userRepo.findByUserId(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found"));
        }

        // Create and save the review
        Review review = new Review(userOptional.get(), productId, userComment, reviewSentiment);
        reviewRepo.save(review);

        // Create the response map to return the saved review with user details
        Map<String, Object> reviewData = new HashMap<>();
        reviewData.put("review_id", review.getReviewId());
        reviewData.put("product_id", review.getProductId());
        reviewData.put("user_name", review.getUser().getUserName());
        reviewData.put("profile_picture_url", review.getUser().getProfilePictureUrl());
        reviewData.put("user_comment", review.getUserComment());
        reviewData.put("review_sentiment", review.getReviewSentiment());
        reviewData.put("created_at", review.getCreatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(reviewData);
    }

    // Endpoint for fetching reviews by productId
    @GetMapping("/get-reviews/{productId}")
    public ResponseEntity<List<Map<String, Object>>> getReviewsByProductId(@PathVariable String productId) {
        List<Review> reviews = reviewRepo.findByProductId(productId);

        // Transform the reviews into the desired response format
        List<Map<String, Object>> response = new ArrayList<>();
        for (Review review : reviews) {
            Map<String, Object> reviewData = new HashMap<>();
            reviewData.put("review_id", review.getReviewId());
            reviewData.put("product_id", review.getProductId());
            reviewData.put("user_name", review.getUser().getUserName());
            reviewData.put("profile_picture_url", review.getUser().getProfilePictureUrl());
            reviewData.put("user_comment", review.getUserComment());
            reviewData.put("review_sentiment", review.getReviewSentiment());
            reviewData.put("created_at", review.getCreatedAt());

            response.add(reviewData);
        }

        return ResponseEntity.ok(response);
    }
}
