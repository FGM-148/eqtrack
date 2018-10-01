package com.ms.et.services;

import com.ms.et.commands.UserForm;
import com.ms.et.domain.User;
import java.util.List;

public interface UserService {
    List<User> listAll();
    User getById(Long id);
    User saveOrUpdate(User user);
    void delete(Long id);
    User saveOrUpdateUserForm(UserForm userForm);
    List<User> fuzzySearch(String q);
}
