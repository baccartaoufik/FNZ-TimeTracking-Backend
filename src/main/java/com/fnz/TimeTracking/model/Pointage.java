package com.fnz.TimeTracking.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
public class Pointage {
    @Id
    @GeneratedValue
    private long pointage_id;
    private String type;
    private String workType;
    private LocalDateTime clockIn;
    private LocalDateTime clockOut;


    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;
}
