package com.ms.et.commands;

import com.ms.et.domain.Role;
import com.ms.et.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserForm {
    private Long id;
    private String name;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private boolean enabled;
    private List<String> roles;

    private RoleService roleService;

//    @Autowired
//    public void setRoleService(RoleService _roleService) {
//        roleService = _roleService;
//    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String _password) {
        password = _password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String _email) {
        email = _email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean _enabled) {
        enabled = _enabled;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> _roles) {
        roles = _roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
//
//    public List<String> getAvailableRoles() {
//        List<Role> allRoles = roleService.listAll();
//        List<String> result = new ArrayList<>();
//        for (Role role : allRoles) {
//            result.add(role.getName());
//        }
//        return result;
//    }
}
