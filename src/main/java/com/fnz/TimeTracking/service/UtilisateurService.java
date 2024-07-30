package com.fnz.TimeTracking.service;
import com.fnz.TimeTracking.model.Role;
import com.fnz.TimeTracking.model.Utilisateur;
import com.fnz.TimeTracking.repository.RoleRepository;
import com.fnz.TimeTracking.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleRepository roleRepository;

    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        if (utilisateur.getRole() != null|| utilisateur.getRole().getNomRole() == null) {
            String nomRole = utilisateur.getRole().getNomRole();
            if (nomRole != null && !nomRole.isEmpty()) {
                Role existingRole = roleRepository.findByNomRole(nomRole);
                utilisateur.setRole(existingRole);
            } else {
                throw new RuntimeException("Role name is required");
            }
        } else {
            throw new RuntimeException("Role is required");
        }
        return utilisateurRepository.save(utilisateur);
    }

    public List<Utilisateur> getAllUtilisateurs() {

        return utilisateurRepository.findAll();
    }

    public Utilisateur getUtilisateurById(Long id) {

        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur not found"));
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

        if (utilisateurDetails.getRole() != null && utilisateurDetails.getRole().getNomRole() != null) {
            Role existingRole = roleRepository.findByNomRole(utilisateurDetails.getRole().getNomRole());
            utilisateur.setRole(existingRole);
        }
        return utilisateurRepository.save(utilisateur);
    }
}
