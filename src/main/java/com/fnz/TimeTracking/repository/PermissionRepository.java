package com.fnz.TimeTracking.repository;

import com.fnz.TimeTracking.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
}
