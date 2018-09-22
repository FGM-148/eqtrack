package com.ms.et.converters;

import com.ms.et.commands.UserForm;
import com.ms.et.domain.Role;
import com.ms.et.domain.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserToUserForm implements Converter<User, UserForm> {
    @Override
    public UserForm convert(User user) {
        UserForm userForm = new UserForm();
        userForm.setId(user.getId());
        userForm.setName(user.getName());
        userForm.setEmail(user.getEmail());
        userForm.setEnabled(user.isEnabled());
        List<String> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roles.add(role.getName());
        }
        userForm.setRoles(roles);
        return userForm;
    }
}
