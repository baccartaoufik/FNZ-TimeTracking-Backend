package com.fnz.TimeTracking.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "utilisateur")
    private Set<Pointage> pointages;

    @OneToMany(mappedBy = "utilisateur")
    private Set<Congé> conges;

    @OneToMany(mappedBy = "utilisateur")
    private Set<Sentiment> sentiments;

    @OneToMany(mappedBy = "utilisateur")
    private Set<Arrivée> arrivees;

    @OneToMany(mappedBy = "utilisateur")
    private Set<Départ> departs;

    @OneToMany(mappedBy = "utilisateur")
    private Set<Permission> permissions;

}
