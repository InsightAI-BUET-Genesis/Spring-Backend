package com.example.InsightAI.Service;

import com.example.InsightAI.Entity.Collection;
import com.example.InsightAI.Interface.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/collections")
public class CollectionService {

    @Autowired
    private CollectionRepository collectionRepo;

    // Create a new collection
    @PostMapping("/create")
    public ResponseEntity<Collection> createCollection(@RequestBody Map<String, String> collectionData) {
        String userId = collectionData.get("userId");
        String name = collectionData.get("name");

        Collection newCollection = new Collection();
        newCollection.setUserId(userId);
        newCollection.setName(name);
        newCollection.setOriginalName(name);

        Collection savedCollection = collectionRepo.save(newCollection);
        return new ResponseEntity<>(savedCollection, HttpStatus.CREATED);
    }

    // Get a specific collection by ID
    @GetMapping("/{id}")
    public ResponseEntity<Collection> getCollection(@PathVariable String id) {
        Optional<Collection> collection = collectionRepo.findById(id);
        if (collection.isPresent()) {
            return new ResponseEntity<>(collection.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a specific collection by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollection(@PathVariable String id) {
        Optional<Collection> collection = collectionRepo.findById(id);
        if (collection.isPresent()) {
            collectionRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update the name of a specific collection by ID
    @PutMapping("/{id}/updateName")
    public ResponseEntity<Collection> updateCollectionName(@PathVariable String id, @RequestBody Map<String, String> request) {
        Optional<Collection> collection = collectionRepo.findById(id);
        if (collection.isPresent()) {
            Collection updatedCollection = collection.get();
            updatedCollection.setName(request.get("name"));
            collectionRepo.save(updatedCollection);
            return new ResponseEntity<>(updatedCollection, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get all collections for a specific user by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Collection>> getCollectionsByUserId(@PathVariable String userId) {
        List<Collection> collections = collectionRepo.findByUserId(userId);
        return new ResponseEntity<>(collections, HttpStatus.OK);
    }

}
