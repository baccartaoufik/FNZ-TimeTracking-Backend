package com.fnz.TimeTracking.repository;

import com.fnz.TimeTracking.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByNomRole(String role);
}
