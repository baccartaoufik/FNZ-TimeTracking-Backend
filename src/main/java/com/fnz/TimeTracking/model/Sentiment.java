package com.fnz.TimeTracking.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Sentiment {

    @Id
    @GeneratedValue
    private long sentiement_Id;
    private String sentiment;

    @ManyToOne
    Utilisateur utilisateur;

}
