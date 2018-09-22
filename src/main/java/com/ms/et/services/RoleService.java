package com.ms.et.services;

import com.ms.et.commands.RoleForm;
import com.ms.et.domain.Role;

import java.util.List;

public interface RoleService {
    List<Role> listAll();
    Role getById(Long id);
    Role getByName(String name);
    Role saveOrUpdate(Role role);
    void delete(Long id);
    Role saveOrUpdateRoleForm(RoleForm roleForm);
}