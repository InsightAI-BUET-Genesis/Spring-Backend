package com.example.InsightAI.Service;

import io.github.thoroldvix.internal.TranscriptApiFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.github.thoroldvix.api.*;

import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/transcription")
public class TranscriptionService {

    //Get youtube transcription
    @PostMapping("/youtube")
    public ResponseEntity<Map<String, String>> getYoutubeTranscription(@RequestBody Map<String, String> videoData){
        String videoId = videoData.get("videoId");

        try {
            // Create a new default YoutubeTranscriptApi instance
            YoutubeTranscriptApi youtubeTranscriptApi = TranscriptApiFactory.createDefault();

            // Retrieve the transcript list for the given video
            TranscriptList transcriptList = youtubeTranscriptApi.listTranscripts(videoId);

            // Find the English transcript (or default to English)
            Transcript transcript = transcriptList.findTranscript("en");

            // Retrieve transcript content
            TranscriptContent transcriptContent = transcript.fetch();

            // Combine all transcript items into a single string
            String fullTranscript = transcriptContent.getContent().stream()
                    .map(TranscriptContent.Fragment::getText)
                    .collect(Collectors.joining(" "));

            // Wrap the response in a JSON object
            return new ResponseEntity<>(Map.of("transcript", fullTranscript), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            // Return an error message in JSON format
            return new ResponseEntity<>(Map.of("error", "Unable to retrieve transcript. Please check the video ID."), HttpStatus.NOT_FOUND);
        }
    }
}
