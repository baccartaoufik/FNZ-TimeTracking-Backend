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
public class Role {

    @Id
    @GeneratedValue
    private long id_Role;
    private String nomRole;
    private String privilege;

    @OneToMany(mappedBy = "role")
    private Set<Utilisateur> utilisateurs;
}
