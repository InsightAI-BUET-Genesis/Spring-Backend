package com.example.InsightAI.Service;

import com.example.InsightAI.Entity.Cart;
import com.example.InsightAI.Interface.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/cart")
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String productId = request.get("productId");

        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.badRequest().body("User ID is required");
        }

        // Find the cart by userId, or create a new one if it doesn't exist
        Cart cart = cartRepository.findById(userId).orElse(new Cart(userId));

        // Add the product to the cart if it's not already there
        if (!cart.getProductIds().contains(productId)) {
            cart.getProductIds().add(productId);
        }

        try {
            Cart savedCart = cartRepository.save(cart);
            return ResponseEntity.ok(savedCart);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving cart: " + e.getMessage());
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<Cart> removeFromCart(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String productId = request.get("productId");

        Optional<Cart> optionalCart = cartRepository.findById(userId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.getProductIds().remove(productId);
            return ResponseEntity.ok(cartRepository.save(cart));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable String userId) {
        return cartRepository.findById(userId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/check")
    public ResponseEntity<Map<String, Boolean>> isProductInCart(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String productId = request.get("productId");

        Optional<Cart> optionalCart = cartRepository.findById(userId);
        boolean isInCart = optionalCart.map(cart -> cart.getProductIds().contains(productId)).orElse(false);
        return ResponseEntity.ok(Collections.singletonMap("isInCart", isInCart));
    }
}
