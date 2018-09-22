package com.ms.et.services;

import com.ms.et.commands.RoleForm;
import com.ms.et.converters.RoleFormToRole;
import com.ms.et.domain.Role;
import com.ms.et.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    private RoleRepository roleRepository;
    private RoleFormToRole roleFormToRole;

    @Autowired
    public RoleServiceImpl(RoleRepository _roleRepository, RoleFormToRole _roleFormToRole) {
        roleRepository = _roleRepository;
        roleFormToRole = _roleFormToRole;
    }

    @Override
    public List<Role> listAll() {
        List<Role> roles = new ArrayList<>();
        roleRepository.findAll().forEach(roles::add);
        return roles;
    }

    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role getByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role saveOrUpdate(Role role) {
        roleRepository.save(role);
        return role;
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role saveOrUpdateRoleForm(RoleForm roleForm) {
        Role savedRole = saveOrUpdate(roleFormToRole.convert(roleForm));
        System.out.println("Saved Role Id: " + savedRole.getId());
        return savedRole;
    }
}
