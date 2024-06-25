package com.fnz.TimeTracking.controller;

import com.fnz.TimeTracking.model.Pointage;
import com.fnz.TimeTracking.service.PointageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pointages")
public class PointageController {

    @Autowired
    private PointageService pointageService;

    @PostMapping
    public ResponseEntity<Pointage> createPointage(@RequestBody Pointage pointage) {
        Pointage savedPointage = pointageService.savePointage(pointage);
        return ResponseEntity.ok(savedPointage);
    }

    @GetMapping
    public ResponseEntity<List<Pointage>> getAllPointages() {
        List<Pointage> pointages = pointageService.getAllPointages();
        return ResponseEntity.ok(pointages);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pointage> updatePointage(@PathVariable Long id, @RequestBody Pointage pointageDetails) {
        Pointage updatedPointage = pointageService.updatePointage(id, pointageDetails);
        return ResponseEntity.ok(updatedPointage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pointage> getPointageById(@PathVariable Long id) {
        Pointage pointage = pointageService.getPointageById(id);
        return ResponseEntity.ok(pointage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePointage(@PathVariable Long id) {
        pointageService.deletePointage(id);
        return ResponseEntity.ok().build();
    }
}
