package com.ms.et.initdata;

import com.ms.et.domain.*;
import com.ms.et.repositories.*;
import com.ms.et.services.CompanyService;
import com.ms.et.services.ItemService;
import com.ms.et.services.ProjectService;
import org.kohsuke.randname.RandomNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
    private ItemRepository itemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ProjectService projectService;

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

        List<String> allPrivsInStirngList = Arrays.asList(
                "READ_ITEM", "WRITE_ITEM", "ITEM_LIST",
                "READ_COMPANY", "WRITE_COMPANY",
                "READ_OWN_PROJECT", "WRITE_OWN_PROJECT",
                "READ_ROLE", "WRITE_ROLE",
                "READ_PRIVILEGE", "WRITE_PRIVILEGE",
                "READ_USER", "WRITE_USERS", "USER_LIST",
                "ITEM_LIST_BY_ADDRESS",
                "ITEM_LIST_BY_PROJECT",
                "ITEM_LIST_BY_COMPANY",
                "ITEM_LIST_BY_USER",
                "PROJECT_LIST_BY_OWNER",
                "ITEM_LIST_BY_OWNER",
                "ASSIGN_ITEM_TO_USER",
                "ASSIGN_ITEM_TO_PROJECT",
                "ASSIGN_ITEM_TO_STORAGE",
                "ASSIGN_PROJECT_AS_OWN",
                "ASSIGN_ITEM_TO_ME");

        List<String> adminPrivsInStringList = Arrays.asList(
                "READ_ROLE", "WRITE_ROLE",
                "READ_PRIVILEGE", "WRITE_PRIVILEGE",
                "READ_USER", "WRITE_USERS", "USER_LIST");

        List<String> ictPrivsInStirngList = Arrays.asList(
                "READ_ITEM", "WRITE_ITEM", "ITEM_LIST",
                "READ_COMPANY", "WRITE_COMPANY",
                "READ_USER",
                "ITEM_LIST_BY_ADDRESS",
                "ITEM_LIST_BY_PROJECT",
                "ITEM_LIST_BY_COMPANY",
                "ITEM_LIST_BY_USER",
                "ASSIGN_ITEM_TO_STORAGE");

        List<String> managerPrivsInStringList = Arrays.asList(
                "READ_ITEM", "ITEM_LIST",
                "READ_OWN_PROJECT", "WRITE_OWN_PROJECT",
                "READ_USER",
                "ITEM_LIST_BY_USER",
                "PROJECT_LIST_BY_OWNER",
                "ITEM_LIST_BY_OWNER",
                "ASSIGN_ITEM_TO_PROJECT",
                "ASSIGN_PROJECT_AS_OWN",
                "ASSIGN_ITEM_TO_ME");

        List<String> userPrivsInStirngList = Arrays.asList(
                "READ_ITEM", "ITEM_LIST",
                "READ_USER",
                "ITEM_LIST_BY_OWNER",
                "ASSIGN_ITEM_TO_ME");

        createPriviligesIfNotFoundFromList(allPrivsInStirngList);

        // == create initial roles
        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege, editPrivilege);
        List<Privilege> rolePrivileges = Arrays.asList(readPrivilege);

        createRoleIfNotFound("ADMINISTRATOR", adminPrivsInStringList);
        createRoleIfNotFound("PROJECT MANAGER", managerPrivsInStringList);
        createRoleIfNotFound("USER", userPrivsInStirngList);
        createRoleIfNotFound("TECHNICAL SPECIALIST", ictPrivsInStirngList);

        Role managerRole = roleRepository.findByName("PROJECT MANAGER");
        User manager = new User();
        manager.setName("b");
        manager.setEmail("manager@test.com");
        manager.setFirstName("Jan");
        manager.setLastName("Kowalski");
        manager.setPassword(passwordEncoder.encode("a"));
        manager.setRoles(Arrays.asList(managerRole));
        manager.setEnabled(true);
        createUserIfNotFound(manager);

        Role adminRole = roleRepository.findByName("ADMINISTRATOR");
        User user = new User();
        user.setName("admin");
        user.setFirstName("Adam");
        user.setLastName("Nowak");
        user.setEmail("admin@test.com");
        user.setPassword(passwordEncoder.encode("ti2018"));
        user.setRoles(Arrays.asList(adminRole));
        user.setEnabled(true);
        createUserIfNotFound(user);

        Role basicRole = roleRepository.findByName("USER");
        User basicUser = new User();
        basicUser.setName("c");
        basicUser.setFirstName("Marek");
        basicUser.setLastName("Marecki");
        basicUser.setEmail("user@test.com");
        basicUser.setPassword(passwordEncoder.encode("a"));
        basicUser.setRoles(Arrays.asList(basicRole));
        basicUser.setEnabled(true);
        createUserIfNotFound(basicUser);

        Role ictRole = roleRepository.findByName("TECHNICAL SPECIALIST");
        User ictUser = new User();
        ictUser.setName("d");
        ictUser.setFirstName("Frodo");
        ictUser.setLastName("Baggins");
        ictUser.setEmail("ict@test.com");
        ictUser.setPassword(passwordEncoder.encode("a"));
        ictUser.setRoles(Arrays.asList(ictRole));
        ictUser.setEnabled(true);
        createUserIfNotFound(ictUser);

        User twoRoles = new User();
        twoRoles.setName("dwierole");
        twoRoles.setFirstName("Rafał");
        twoRoles.setLastName("Nafalski");
        twoRoles.setEmail("rafal@test.com");
        twoRoles.setPassword(passwordEncoder.encode("a"));
        twoRoles.setRoles(Arrays.asList(adminRole, ictRole));
        twoRoles.setEnabled(true);
        createUserIfNotFound(twoRoles);
        createCompany();
        createProjects(manager);
        createItems();

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
    private Role createRoleIfNotFound(String name, List<String> privilegesInStrings) {
        Role role = roleRepository.findByName(name);
        List<Privilege> privileges = new ArrayList<>();
        if (role == null) {
            role = new Role();
            role.setName(name);
            for (String p : privilegesInStrings) {
                Privilege priv = privilegeRepository.findByName(p);
                privileges.add(priv);
            }
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    private void createPriviligesIfNotFoundFromList(List<String> pList) {
        List<Privilege> privs = new ArrayList<>();
        for (String pName : pList) {
            if (privilegeRepository.findByName(pName)==null) {
                Privilege p = new Privilege();
                p.setName(pName);
                privs.add(p);
            }
        }
        privilegeRepository.saveAll(privs);
    }

    @Transactional
    private Address createAddress() {
        RandomNameGenerator rnd = new RandomNameGenerator(323);
        Address address = new Address();
        address.setStreet(rnd.next());
        address.setCity(rnd.next());
        address.setCountry("Poland");
        Random r = new Random();
        address.setNumber(new Integer(r.nextInt((1000 - 1) + 1) + 1).toString());
        address.setPostalCode(r.nextInt((1000 - 1) + 1) + 1);
        addressRepository.save(address);
        return address;

    }

    @Transactional
    private void createItems() {
        RandomNameGenerator rnd = new RandomNameGenerator(0);

        for (int i = 0; i < 100; i++) {
            Item item = new Item();
            item.setInternalNumber("F1000" + i);
            item.setName(rnd.next());
            item.setSerialNumber("S/N 23847" + rnd.next());
            Address address = createAddress();
            item.setSourceOfDelivery(address);
            item.setDeliveryDate(new Date(30, 30, 2000));
            if (i%2 == 0) {
                item.setInStorage(true);
            }
            else {
                item.setInStorage(false);
                item.setProject(projectService.getById(new Long (1)));
            }
            itemService.saveOrUpdate(item);
        }
    }

    @Transactional
    private void createCompany() {
        RandomNameGenerator rnd = new RandomNameGenerator(11);
        Company company = new Company();
        company.setAddress(createAddress());
        company.setName("teito");
        companyService.saveOrUpdate(company);

        for (int i =0; i<10; i++) {
            Company companyr = new Company();
            companyr.setAddress(createAddress());
            companyr.setName(rnd.next());
            companyService.saveOrUpdate(companyr);
        }

    }

    @Transactional
    private void createProjects(User user) {
        RandomNameGenerator rnd = new RandomNameGenerator(45);
        for (int i = 0; i < 5; i++) {
            Project project = new Project();
            project.setName(rnd.next());
            project.setCompany(companyService.getById(new Long(i)));
            project.setUser(user);
            projectService.saveOrUpdate(project);
        }
    }

}
