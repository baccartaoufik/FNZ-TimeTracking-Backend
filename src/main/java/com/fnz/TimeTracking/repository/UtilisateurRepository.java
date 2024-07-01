package com.fnz.TimeTracking.repository;

import com.fnz.TimeTracking.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
}
