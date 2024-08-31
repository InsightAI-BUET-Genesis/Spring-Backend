package com.example.InsightAI.Interface;

import com.example.InsightAI.Entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File,String> {
    List<File> findByCollectionId(String collectionId);
}
