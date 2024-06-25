package com.fnz.TimeTracking.service;

import com.fnz.TimeTracking.model.Pointage;
import com.fnz.TimeTracking.model.Utilisateur;
import com.fnz.TimeTracking.repository.PointageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointageService {

    @Autowired
    private PointageRepository pointageRepository;

    public Pointage savePointage(Pointage pointage) {
        return pointageRepository.save(pointage);
    }

    public List<Pointage> getAllPointages() {
        return pointageRepository.findAll();
    }

    public Pointage getPointageById(Long id) {
        return pointageRepository.findById(id).orElse(null);
    }

    public void deletePointage(Long id) {
        pointageRepository.deleteById(id);
    }

    public Pointage updatePointage(Long id, Pointage pointageDetails) {
        Pointage pointage = pointageRepository.findById(id).orElseThrow();
        pointage.setType(pointageDetails.getType());
        pointage.setWorkType(pointageDetails.getWorkType());
        return pointageRepository.save(pointage);
    }
}
