package com.fnz.TimeTracking.service;

import com.fnz.TimeTracking.model.Arrivée;
import com.fnz.TimeTracking.repository.ArriveeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArriveeService {

    @Autowired
    private ArriveeRepository arriveeRepository;

    public Arrivée saveArrivee(Arrivée arrivée) {
        return arriveeRepository.save(arrivée);
    }

    public List<Arrivée> getAllArrivees() {
        return arriveeRepository.findAll();
    }

    public Arrivée getArriveeById(Long id) {
        return arriveeRepository.findById(id).orElse(null);
    }

    public void deleteArrivee(Long id) {
        arriveeRepository.deleteById(id);
    }

    public Arrivée updateArrivee(Long id, Arrivée arriveeDetails) {
        Arrivée arrivee = arriveeRepository.findById(id).orElseThrow();
        arrivee.setDate_arrivée(arriveeDetails.getDate_arrivée());
        arrivee.setHeure_arrivée(arriveeDetails.getHeure_arrivée());
        return arriveeRepository.save(arrivee);
    }

}
