package com.fnz.TimeTracking.service;
import com.fnz.TimeTracking.model.Sentiment;
import com.fnz.TimeTracking.repository.SentimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SentimentService {

    @Autowired
    private SentimentRepository sentimentRepository;


    public Sentiment saveSentiment(Sentiment sentiment) {
        return sentimentRepository.save(sentiment);
    }

    public List<Sentiment> getAllSentiments() {
        return sentimentRepository.findAll();
    }

    public Sentiment getSentimentById(Long id) {
        return sentimentRepository.findById(id).orElse(null);
    }

    public void deleteSentiment(Long id) {
        sentimentRepository.deleteById(id);
    }
    public Sentiment updateSentiment(Long id, Sentiment sentimentDetails) {
        Sentiment sentiment = sentimentRepository.findById(id).orElseThrow();
        sentiment.setSentiment(sentimentDetails.getSentiment());
        return sentimentRepository.save(sentiment);
    }

}
