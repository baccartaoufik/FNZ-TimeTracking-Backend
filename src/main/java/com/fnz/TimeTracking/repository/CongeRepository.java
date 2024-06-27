package com.fnz.TimeTracking.repository;

import com.fnz.TimeTracking.model.Congé;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CongeRepository extends JpaRepository<Congé,Long> {
}
