package com.fnz.TimeTracking.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Permission {

    @Id
    @GeneratedValue
    private long id;
    private LocalDateTime heureSortie;
    private LocalDateTime heureRetour;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

}
