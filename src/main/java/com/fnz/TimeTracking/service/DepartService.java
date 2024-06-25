package com.fnz.TimeTracking.service;

import com.fnz.TimeTracking.model.Départ;
import com.fnz.TimeTracking.repository.DepartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartService {

    @Autowired
    private DepartRepository departRepository;

    public Départ saveDepart(Départ depart) {
        return departRepository.save(depart);
    }

    public List<Départ> getAllDeparts() {
        return departRepository.findAll();
    }

    public Départ getDepartById(Long id) {
        return departRepository.findById(id).orElse(null);
    }

    public void deleteDepart(Long id) {
        departRepository.deleteById(id);
    }
    public Départ updateDepart(Long id, Départ departDetails) {
        Départ depart = departRepository.findById(id).orElseThrow();
        depart.setDate_départ(departDetails.getDate_départ());
        depart.setHeure_départ(departDetails.getHeure_départ());
        return departRepository.save(depart);
    }


}
