package com.ms.et.initdata;

import com.ms.et.domain.Privilege;
import com.ms.et.domain.Role;
import com.ms.et.domain.User;
import com.ms.et.repositories.PrivilegeRepository;
import com.ms.et.repositories.RoleRepository;
import com.ms.et.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class InitDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        // == create initial privileges
        Privilege readPrivilege = createPrivilegeIfNotFound("READ_ITEM_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_ITEM_PRIVILEGE");
        Privilege editPrivilege = createPrivilegeIfNotFound("EDIT_ITEM_PRIVILEGE");
        Privilege sadminPrivilege = createPrivilegeIfNotFound("SUPERADMIN_PRIVILEGE");

        // == create initial roles
        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege, editPrivilege);
        List<Privilege> sadminPrivileges = Arrays.asList(sadminPrivilege, readPrivilege, writePrivilege, editPrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        List<Privilege> rolePrivileges = Arrays.asList(readPrivilege);
        createRoleIfNotFound("ROLE_USER", rolePrivileges);
        createRoleIfNotFound("ROLE_SADMIN", sadminPrivileges);

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User user = new User();
        user.setName("admin");
        user.setEmail("admin@test.com");
        user.setPassword(passwordEncoder.encode("ti2018"));
        user.setRoles(Arrays.asList(adminRole));
        user.setEnabled(true);
        createUserIfNotFound(user);

        Role basicRole = roleRepository.findByName("ROLE_USER");
        User basicUser = new User();
        basicUser.setName("user");
        basicUser.setEmail("user@test.com");
        basicUser.setPassword(passwordEncoder.encode("user"));
        basicUser.setRoles(Arrays.asList(basicRole));
        basicUser.setEnabled(true);
        createUserIfNotFound(basicUser);

        Role sadminRole = roleRepository.findByName("ROLE_SADMIN");
        User sadmin = new User();
        sadmin.setName("sadmin");
        sadmin.setEmail("sadmin@test.com");
        sadmin.setPassword(passwordEncoder.encode("a"));
        sadmin.setRoles(Arrays.asList(sadminRole));

        sadmin.setEnabled(true);
        createUserIfNotFound(sadmin);

        alreadySetup = true;
    }

    @Transactional
    private User createUserIfNotFound(User user) {
        if (userRepository.findByName(user.getName()) != null) return null;
        userRepository.save(user);
        return user;
    }

    @Transactional
    private Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege();
            privilege.setName(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    private Role createRoleIfNotFound(String name, List<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

}
