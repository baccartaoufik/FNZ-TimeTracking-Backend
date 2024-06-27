package com.fnz.TimeTracking.repository;

import com.fnz.TimeTracking.model.Pointage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointageRepository extends JpaRepository<Pointage,Long> {
}
