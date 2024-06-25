package com.fnz.TimeTracking.service;
import com.fnz.TimeTracking.model.Permission;
import com.fnz.TimeTracking.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    public Permission savePermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    public Permission getPermissionById(Long id) {
        return permissionRepository.findById(id).orElse(null);
    }

    public void deletePermission(Long id) {
        permissionRepository.deleteById(id);
    }

    public Permission updatePermission(Long id, Permission permissionDetails) {
        Permission permission = permissionRepository.findById(id).orElseThrow();
        permission.setHeureSortie(permissionDetails.getHeureSortie());
        permission.setHeureRetour(permissionDetails.getHeureRetour());
        return permissionRepository.save(permission);
    }

}
