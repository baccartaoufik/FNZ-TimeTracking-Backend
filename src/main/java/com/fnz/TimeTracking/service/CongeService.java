package com.fnz.TimeTracking.service;

import com.fnz.TimeTracking.model.Congé;
import com.fnz.TimeTracking.repository.CongeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CongeService {

    @Autowired
    private CongeRepository congeRepository;

    public Congé saveConge(Congé congé) {
        return congeRepository.save(congé);
    }

    public List<Congé> getAllCongés() {
        return congeRepository.findAll();
    }

    public Congé getCongéById(Long id) {
        return congeRepository.findById(id).orElse(null);
    }

    public void deleteCongé(Long id) {
        congeRepository.deleteById(id);
    }

    public Congé updateCongé(Long id, Congé congéDetails) {
        Congé congé = congeRepository.findById(id).orElseThrow();
        congé.setDateDebut(congéDetails.getDateDebut());
        congé.setDateFin(congéDetails.getDateFin());
        congé.setAbsenceType(congéDetails.getAbsenceType());
        return congeRepository.save(congé);
    }
}
