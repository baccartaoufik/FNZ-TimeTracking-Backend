package com.fnz.TimeTracking.repository;

import com.fnz.TimeTracking.model.Sentiment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentimentRepository extends JpaRepository<Sentiment, Long> {
}
