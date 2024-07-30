package com.fnz.TimeTracking.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Role;
    @Column(unique = true)
    private String nomRole;
    private String privilege;

    @OneToMany(mappedBy = "role" , cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Utilisateur> utilisateurs;
}
