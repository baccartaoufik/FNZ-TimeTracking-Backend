package com.fnz.TimeTracking.service;


import com.fnz.TimeTracking.model.Role;
import com.fnz.TimeTracking.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    public Role updateRole(Long id, Role roleDetails) {
        Role role = roleRepository.findById(id).orElseThrow();
        role.setNomRole(roleDetails.getNomRole());
        role.setPrivilege(roleDetails.getPrivilege());
        return roleRepository.save(role);
    }

}
