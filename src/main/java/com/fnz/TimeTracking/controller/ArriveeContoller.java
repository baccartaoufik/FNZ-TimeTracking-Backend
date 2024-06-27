package com.fnz.TimeTracking.controller;

import com.fnz.TimeTracking.model.Arrivée;
import com.fnz.TimeTracking.service.ArriveeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/arrivees")
public class ArriveeContoller {
    @Autowired
    private ArriveeService arriveeService;

    @PostMapping
    public ResponseEntity<Arrivée> createArrivee(@RequestBody Arrivée arrivee) {
        Arrivée savedArrivee = arriveeService.saveArrivee(arrivee);
        return ResponseEntity.ok(savedArrivee);
    }

    @GetMapping
    public ResponseEntity<List<Arrivée>> getAllArrivees() {
        List<Arrivée> arrivees = arriveeService.getAllArrivees();
        return ResponseEntity.ok(arrivees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Arrivée> getArriveeById(@PathVariable Long id) {
        Arrivée arrivee = arriveeService.getArriveeById(id);
        return ResponseEntity.ok(arrivee);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Arrivée> updateArrivee(@PathVariable Long id, @RequestBody Arrivée arriveeDetails) {
        Arrivée updatedArrivee = arriveeService.updateArrivee(id, arriveeDetails);
        return ResponseEntity.ok(updatedArrivee);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArrivee(@PathVariable Long id) {
        arriveeService.deleteArrivee(id);
        return ResponseEntity.ok().build();
    }
}
