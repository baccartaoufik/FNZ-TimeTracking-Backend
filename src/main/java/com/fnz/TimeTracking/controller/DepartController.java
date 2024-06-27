package com.fnz.TimeTracking.controller;

import com.fnz.TimeTracking.model.Départ;
import com.fnz.TimeTracking.service.DepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departs")
public class DepartController {
    @Autowired
    private DepartService departService;

    @PostMapping
    public ResponseEntity<Départ> createDepart(@RequestBody Départ depart) {
        Départ savedDepart = departService.saveDepart(depart);
        return ResponseEntity.ok(savedDepart);
    }

    @GetMapping
    public ResponseEntity<List<Départ>> getAllDeparts() {
        List<Départ> departs = departService.getAllDeparts();
        return ResponseEntity.ok(departs);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Départ> updateDepart(@PathVariable Long id, @RequestBody Départ departDetails) {
        Départ updatedDepart = departService.updateDepart(id, departDetails);
        return ResponseEntity.ok(updatedDepart);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Départ> getDepartById(@PathVariable Long id) {
        Départ depart = departService.getDepartById(id);
        return ResponseEntity.ok(depart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepart(@PathVariable Long id) {
        departService.deleteDepart(id);
        return ResponseEntity.ok().build();
    }
}
