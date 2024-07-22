package com.fnz.TimeTracking.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Role;
    private String nomRole;
    private String privilege;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private Set<Utilisateur> utilisateurs;
}
