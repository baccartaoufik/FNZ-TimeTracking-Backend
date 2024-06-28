package com.fnz.TimeTracking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class Utilisateur {
    @Id
    @GeneratedValue
    private long idUtilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String titre;
    private String photo;
    private String sexe;
    private String role;
    private String département;
    // Implement the getUsername and getPassword methods
    @Getter
    private String username; // Add a username field
    @Getter
    private String password; // Add a password field

    @OneToMany(cascade = CascadeType.ALL, mappedBy="utilisateur")
    private Set<Pointage> Pointages;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Congé> congés;


    @OneToMany(cascade = CascadeType.ALL, mappedBy="utilisateur")
    private Set<Sentiment> Sentiments;


    @ManyToOne
    @JoinColumn(name = "id_role")
    private Role roles;

    public boolean isValid() {
        // Example validity check: all fields must be non-null
        return nom != null && prenom != null && email != null && telephone != null &&
                titre != null && photo != null && sexe != null && role != null &&
                département != null && username != null && password != null;
    }
}
