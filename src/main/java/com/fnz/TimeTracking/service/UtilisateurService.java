package com.fnz.TimeTracking.service;
import com.fnz.TimeTracking.model.Utilisateur;
import com.fnz.TimeTracking.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {

        return utilisateurRepository.save(utilisateur);
    }

    public List<Utilisateur> getAllUtilisateurs() {

        return utilisateurRepository.findAll();
    }

    public Utilisateur getUtilisateurById(Long id) {

        return utilisateurRepository.findById(id).orElse(null);
    }

    public void deleteUtilisateur(Long id) {

        utilisateurRepository.deleteById(id);
    }

    public Utilisateur updateUtilisateur(Long id, Utilisateur utilisateurDetails) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow();
        utilisateur.setNom(utilisateurDetails.getNom());
        utilisateur.setPrenom(utilisateurDetails.getPrenom());
        utilisateur.setEmail(utilisateurDetails.getEmail());
        utilisateur.setTelephone(utilisateurDetails.getTelephone());
        utilisateur.setTitre(utilisateurDetails.getTitre());
        utilisateur.setPhoto(utilisateurDetails.getPhoto());
        utilisateur.setSexe(utilisateurDetails.getSexe());
        utilisateur.setRole(utilisateurDetails.getRole());
        //utilisateur.setDepartment(utilisateurDetails.getDepartment());
        return utilisateurRepository.save(utilisateur);
    }
}
