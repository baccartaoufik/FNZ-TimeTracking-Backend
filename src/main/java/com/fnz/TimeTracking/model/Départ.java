package com.fnz.TimeTracking.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity

public class Départ {

    @Id
    @GeneratedValue
    private long id;
    private LocalDate date_départ;
    private LocalDateTime heure_départ;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;


}
