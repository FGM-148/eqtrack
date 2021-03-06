package com.ms.et.services;

import com.ms.et.commands.UserForm;
import com.ms.et.converters.UserFormToUser;
import com.ms.et.domain.User;
import com.ms.et.repositories.UserRepository;
import com.ms.et.services.specification.SearchOperation;
import com.ms.et.services.specification.SpecSearchCriteria;
import com.ms.et.services.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserFormToUser userFormToUser;

    @Autowired
    public UserServiceImpl(UserRepository _userRepository, UserFormToUser _userFormToUser) {
        userRepository = _userRepository;
        userFormToUser = _userFormToUser;
    }

    @Override
    public List<User> listAll() {
        List<User> users = new ArrayList();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public User saveOrUpdate(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public User saveOrUpdateUserForm(UserForm userForm) {
        User savedUser = saveOrUpdate(userFormToUser.convert(userForm));
        System.out.println("Saved User Id: " + savedUser.getId());
        return savedUser;
    }

    @Override
    public List<User> fuzzySearch(String q) {
        UserSpecification spec1 =
                new UserSpecification(new SpecSearchCriteria("firstName", SearchOperation.CONTAINS, q));
        UserSpecification spec2 =
                new UserSpecification(new SpecSearchCriteria("lastName", SearchOperation.CONTAINS, q));
        UserSpecification spec3 =
                new UserSpecification(new SpecSearchCriteria("email", SearchOperation.CONTAINS, q));
        UserSpecification spec4 =
                new UserSpecification(new SpecSearchCriteria("name", SearchOperation.CONTAINS, q));

        List<User> results = userRepository.findAll(Specifications.where(spec1).or(spec2).or(spec3).or(spec4));
        return results;
    }
}
