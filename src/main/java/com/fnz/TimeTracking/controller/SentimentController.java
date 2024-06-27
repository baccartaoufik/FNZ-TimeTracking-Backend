package com.fnz.TimeTracking.controller;

import com.fnz.TimeTracking.model.Sentiment;
import com.fnz.TimeTracking.service.SentimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sentiments")
public class SentimentController {
    @Autowired
    private SentimentService sentimentService;

    @PostMapping
    public ResponseEntity<Sentiment> createSentiment(@RequestBody Sentiment sentiment) {
        Sentiment savedSentiment = sentimentService.saveSentiment(sentiment);
        return ResponseEntity.ok(savedSentiment);
    }

    @GetMapping
    public ResponseEntity<List<Sentiment>> getAllSentiments() {
        List<Sentiment> sentiments = sentimentService.getAllSentiments();
        return ResponseEntity.ok(sentiments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sentiment> updateSentiment(@PathVariable Long id, @RequestBody Sentiment sentimentDetails) {
        Sentiment updatedSentiment = sentimentService.updateSentiment(id, sentimentDetails);
        return ResponseEntity.ok(updatedSentiment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sentiment> getSentimentById(@PathVariable Long id) {
        Sentiment sentiment = sentimentService.getSentimentById(id);
        return ResponseEntity.ok(sentiment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSentiment(@PathVariable Long id) {
        sentimentService.deleteSentiment(id);
        return ResponseEntity.ok().build();
    }
}
