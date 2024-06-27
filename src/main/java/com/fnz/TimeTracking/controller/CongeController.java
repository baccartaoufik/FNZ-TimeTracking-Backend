package com.fnz.TimeTracking.controller;

import com.fnz.TimeTracking.model.Congé;
import com.fnz.TimeTracking.service.CongeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conges")
public class CongeController {
    @Autowired
    private CongeService congéService;

    @PostMapping
    public ResponseEntity<Congé> createCongé(@RequestBody Congé congé) {
        Congé savedCongé = congéService.saveConge(congé);
        return ResponseEntity.ok(savedCongé);
    }

    @GetMapping
    public ResponseEntity<List<Congé>> getAllCongés() {
        List<Congé> conges = congéService.getAllCongés();
        return ResponseEntity.ok(conges);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Congé> updateCongé(@PathVariable Long id, @RequestBody Congé congéDetails) {
        Congé updatedCongé = congéService.updateCongé(id, congéDetails);
        return ResponseEntity.ok(updatedCongé);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Congé> getCongéById(@PathVariable Long id) {
        Congé congé = congéService.getCongéById(id);
        return ResponseEntity.ok(congé);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCongé(@PathVariable Long id) {
        congéService.deleteCongé(id);
        return ResponseEntity.ok().build();
    }
}
