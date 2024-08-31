package com.example.InsightAI.Service;

import com.example.InsightAI.Entity.QuestionSet;
import com.example.InsightAI.Interface.QuestionSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/question-sets")
public class QuestionSetService {

    @Autowired
    private QuestionSetRepository questionSetRepo;

    // Create a new QuestionSet
    @PostMapping("/create")
    public ResponseEntity<QuestionSet> createQuestionSet(@RequestBody Map<String, Object> requestData) {
        QuestionSet questionSet = new QuestionSet();
        questionSet.setTitle((String) requestData.get("title"));
        questionSet.setType((String) requestData.get("type"));
        questionSet.setQuestionCount((Integer) requestData.get("questionCount"));
        questionSet.setResourceId((String) requestData.get("resourceId"));
        questionSet.setQuestions((String) requestData.get("questions"));

        questionSet.setTopicType((String) requestData.get("topicType"));
        questionSet.setQuestionType((String) requestData.get("questionType"));
        questionSet.setAnswerType((String) requestData.get("answerType"));
        questionSet.setDifficultyLevel((String) requestData.get("difficultyLevel"));

        questionSet.setCreatedAt(LocalDateTime.now());

        QuestionSet savedQuestionSet = questionSetRepo.save(questionSet);
        return new ResponseEntity<>(savedQuestionSet, HttpStatus.CREATED);
    }

    // Get all QuestionSets by resourceId
    @GetMapping("/resource/{resourceId}")
    public ResponseEntity<List<QuestionSet>> getQuestionSetsByResourceId(@PathVariable String resourceId) {
        List<QuestionSet> questionSets = questionSetRepo.findByResourceId(resourceId);
        if (questionSets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(questionSets, HttpStatus.OK);
    }

    // Get a specific QuestionSet by ID
    @GetMapping("/{id}")
    public ResponseEntity<QuestionSet> getQuestionSetById(@PathVariable Long id) {
        Optional<QuestionSet> questionSet = questionSetRepo.findById(id);
        if (questionSet.isPresent()) {
            return new ResponseEntity<>(questionSet.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Edit the title of a QuestionSet
    @PutMapping("/{id}")
    public ResponseEntity<QuestionSet> editTitle(@PathVariable Long id, @RequestBody Map<String, String> requestData) {
        Optional<QuestionSet> questionSet = questionSetRepo.findById(id);
        if (questionSet.isPresent()) {
            QuestionSet existingQuestionSet = questionSet.get();
            if(requestData.containsKey("title")) {
                existingQuestionSet.setTitle(requestData.get("title"));
            }

            if(requestData.containsKey("highScore")) {
                existingQuestionSet.setHighScore(Integer.valueOf(requestData.get("highScore")));
            }
            QuestionSet updatedQuestionSet = questionSetRepo.save(existingQuestionSet);
            return new ResponseEntity<>(updatedQuestionSet, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete a QuestionSet by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestionSet(@PathVariable Long id) {
        Optional<QuestionSet> questionSet = questionSetRepo.findById(id);
        if (questionSet.isPresent()) {
            questionSetRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
