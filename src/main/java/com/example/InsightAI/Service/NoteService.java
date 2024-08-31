package com.example.InsightAI.Service;

import com.example.InsightAI.Entity.Note;
import com.example.InsightAI.Interface.NoteRepository;
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
@RequestMapping("/notes")
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    // Create a new note
    @PostMapping("/create")
    public ResponseEntity<Note> createNote(@RequestBody Map<String, String> requestBody) {
        Note note = new Note();
        note.setTitle(requestBody.get("title"));
        note.setTopic(requestBody.get("topic"));
        note.setResourceId(requestBody.get("resourceId"));
        note.setKnowledgeLevel(requestBody.get("knowledgeLevel"));
        note.setNote(requestBody.get("note"));
        note = noteRepository.save(note);
        return new ResponseEntity<>(note, HttpStatus.CREATED);
    }

    // Edit the title of a note by ID
    @PutMapping("/{id}")
    public ResponseEntity<Note> editNoteTitle(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()) {
            Note note = optionalNote.get();
            note.setTitle(requestBody.get("title"));
            note = noteRepository.save(note);
            return new ResponseEntity<>(note, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a note by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()) {
            noteRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get a note by ID
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()) {
            return new ResponseEntity<>(optionalNote.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get all notes under a specific resourceId
    @GetMapping("/resource/{resourceId}")
    public ResponseEntity<List<Note>> getNotesByResourceId(@PathVariable String resourceId) {
        List<Note> notes = noteRepository.findByResourceId(resourceId);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }
}
