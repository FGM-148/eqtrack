package com.ms.et.converters;

import com.ms.et.commands.RoleForm;
import com.ms.et.domain.Privilege;
import com.ms.et.domain.Role;
import com.ms.et.services.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleFormToRole implements Converter<RoleForm, Role> {

    private PrivilegeService privilegeService;

    @Autowired
    public void setPrivilegeService(PrivilegeService _privilegeService) {
        privilegeService = _privilegeService;
    }

    @Override
    public Role convert(RoleForm roleForm) {
        Role role = new Role();
        if (roleForm.getId() != null && !StringUtils.isEmpty(roleForm.getId())) {
            role.setId(new Long(roleForm.getId()));
        }
        role.setName(roleForm.getName());
        List<String> privilegesStringList = roleForm.getPrivileges();
        List<Privilege> privileges = new ArrayList();
        for(String privilege : privilegesStringList) {
            privileges.add(privilegeService.getByName(privilege));
        }
        role.setPrivileges(privileges);
        return role;
    }
}
