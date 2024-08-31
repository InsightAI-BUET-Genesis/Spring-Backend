package com.example.InsightAI.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @ElementCollection
    @CollectionTable(name = "cart_products", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "product_id")
    private Set<String> productIds = new HashSet<>();

    // Constructor to initialize the cart with a userId
    public Cart(String userId) {
        this.userId = userId;
    }
}
