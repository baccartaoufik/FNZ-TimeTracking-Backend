package com.fnz.TimeTracking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
public class Congé {

    @Id
    @GeneratedValue
    private long id_Conge;
    private String absenceType;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    @ManyToOne
    private Utilisateur utilisateur;



}
