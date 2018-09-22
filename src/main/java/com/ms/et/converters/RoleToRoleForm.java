package com.ms.et.converters;

import com.ms.et.commands.RoleForm;
import com.ms.et.domain.Privilege;
import com.ms.et.domain.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleToRoleForm implements Converter<Role, RoleForm> {
    @Override
    public RoleForm convert(Role role) {
        RoleForm roleForm = new RoleForm();
        roleForm.setId(role.getId());
        roleForm.setName(role.getName());
        List<String> privileges = new ArrayList<>();
        for (Privilege privilege : role.getPrivileges()) {
            privileges.add(privilege.getName());
        }
        roleForm.setPrivileges(privileges);
        return roleForm;
    }
}
