package com.example.InsightAI.Interface;

import com.example.InsightAI.Entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection,String> {
    List<Collection> findByUserId(String userId);
}
