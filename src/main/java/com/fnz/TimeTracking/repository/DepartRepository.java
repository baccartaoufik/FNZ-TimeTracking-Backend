package com.fnz.TimeTracking.repository;

import com.fnz.TimeTracking.model.Départ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartRepository extends JpaRepository<Départ,Long> {
}
