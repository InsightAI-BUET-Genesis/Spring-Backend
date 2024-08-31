package com.example.InsightAI.Service;

import com.example.InsightAI.Entity.Collection;
import com.example.InsightAI.Entity.File;
import com.example.InsightAI.Interface.CollectionRepository;
import com.example.InsightAI.Interface.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/files")
public class FileService {

    @Autowired
    private FileRepository fileRepo;

    @Autowired
    private CollectionRepository collectionRepo;

    // Create a new file and set the collection name
    @PostMapping("/create")
    public ResponseEntity<File> createFile(@RequestBody Map<String, String> fileData) {

        String collectionId = fileData.get("collectionId");
        String name = fileData.get("name");
        String type = fileData.get("type");
        String youtubeUrl = fileData.get("youtubeUrl");
        String url = fileData.get("url");

        File newFile = new File();
        newFile.setCollectionId(collectionId);
        newFile.setName(name);
        newFile.setType(type);
        newFile.setYoutubeUrl(youtubeUrl);
        newFile.setUrl(url);

        Optional<Collection> collectionOptional = collectionRepo.findById(collectionId);

        if (!collectionOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Collection collection = collectionOptional.get();

        newFile.setId(UUID.randomUUID().toString());
        newFile.setCollectionName(collection.getName()); // Setting the collection name
        fileRepo.save(newFile);

        // Update the collection count
        int currentCount = collection.getCount();
        collection.setCount(currentCount + 1);
        collectionRepo.save(collection);

        return new ResponseEntity<>(newFile, HttpStatus.CREATED);
    }

    // Get a file by ID
    @GetMapping("/{fileId}")
    public ResponseEntity<File> getFileById(@PathVariable String fileId) {
        Optional<File> fileOptional = fileRepo.findById(fileId);
        return fileOptional.map(file -> new ResponseEntity<>(file, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete a file and update the collection count
    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable String fileId) {
        Optional<File> fileOptional = fileRepo.findById(fileId);

        if (!fileOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        File file = fileOptional.get();
        String collectionId = file.getCollectionId();

        fileRepo.delete(file);

        // Update the collection count
        Optional<Collection> collectionOptional = collectionRepo.findById(collectionId);
        if (collectionOptional.isPresent()) {
            Collection collection = collectionOptional.get();
            int currentCount = collection.getCount();
            collection.setCount(currentCount - 1);
            collectionRepo.save(collection);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Update a file's name
    @PutMapping("/{fileId}/updateName")
    public ResponseEntity<File> updateFileName(@PathVariable String fileId, @RequestBody Map<String, String> request) {
        Optional<File> fileOptional = fileRepo.findById(fileId);

        if (fileOptional.isPresent()) {
            File file = fileOptional.get();
            file.setName(request.get("name"));
            fileRepo.save(file);
            return new ResponseEntity<>(file, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get all files for a specific collection
    @GetMapping("/collection/{collectionId}")
    public ResponseEntity<List<File>> getFilesByCollectionId(@PathVariable String collectionId) {
        List<File> files = fileRepo.findByCollectionId(collectionId);
        return new ResponseEntity<>(files, HttpStatus.OK);
    }
}
