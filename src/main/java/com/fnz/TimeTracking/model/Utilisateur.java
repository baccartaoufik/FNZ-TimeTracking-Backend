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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUtilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String titre;
    private String photo;
    private String sexe;
    private String département;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="utilisateur")
    private Set<Pointage> Pointages;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Congé> congés;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="utilisateur")
    private Set<Sentiment> Sentiments;

    @ManyToOne
    @JoinColumn(name = "id_role")
    private Role role;

    public boolean isValid() {
        return nom != null && prenom != null && email != null && telephone != null &&
                titre != null && photo != null && sexe != null &&
                département != null ;
    }


}
