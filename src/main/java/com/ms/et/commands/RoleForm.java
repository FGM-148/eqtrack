package com.ms.et.commands;

import com.ms.et.domain.Privilege;
import com.ms.et.services.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class RoleForm {
    private Long id;
    private String name;
    private List<String> privileges;

    private PrivilegeService privilegeService;

    @Autowired
    public void setPrivilegeService(PrivilegeService _privilegeService) {
        privilegeService = _privilegeService;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long _id) {
        id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        name = _name;
    }

    public List<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<String> _privileges) {
        privileges = _privileges;
    }

//    public List<String> getAvailablePrivileges() {
//        List<Privilege> allPrivileges = privilegeService.listAll();
//        List<String> result = new ArrayList<>();
//        for (Privilege privilege : allPrivileges) {
//            result.add(privilege.getName());
//        }
//        return result;
//    }
}
