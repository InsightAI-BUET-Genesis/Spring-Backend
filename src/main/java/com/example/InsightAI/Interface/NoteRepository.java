package com.example.InsightAI.Interface;

import com.example.InsightAI.Entity.Note;
import com.example.InsightAI.Entity.QuestionSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long>{
    List<Note> findByResourceId(String resourceId);
}
