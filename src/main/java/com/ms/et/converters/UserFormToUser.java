package com.ms.et.converters;

import com.ms.et.commands.UserForm;
import com.ms.et.domain.Role;
import com.ms.et.domain.User;
import com.ms.et.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserFormToUser implements Converter<UserForm, User> {

    private PasswordEncoder encoder;
    private RoleService roleService;

    @Autowired
    @Lazy
    public void setEncoder(PasswordEncoder _encoder) {
        encoder = _encoder;
    }

    @Autowired
    public void setRoleService(RoleService _roleService) {
        roleService = _roleService;
    }


    @Override
    public User convert(UserForm userForm) {
        User user = new User();
        if (userForm.getId() != null && !StringUtils.isEmpty(userForm.getId())) {
            user.setId(new Long(userForm.getId()));
        }
        user.setName(userForm.getName());
        user.setPassword(encoder.encode(userForm.getPassword()));
        user.setEmail(userForm.getEmail());
        user.setEnabled(userForm.isEnabled());
        List<String> rolesStringList = userForm.getRoles();
        List<Role> roles = new ArrayList();
        for(String role : rolesStringList) {
            roles.add(roleService.getByName(role));
        }
        user.setRoles(roles);
        return user;
    }
}
