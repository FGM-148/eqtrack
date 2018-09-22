package com.ms.et.services;

import com.ms.et.domain.Privilege;
import com.ms.et.repositories.PrivilegeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    private PrivilegeRepository privilegeRepository;

    @Override
    public List<Privilege> listAll() {
        List<Privilege> privileges = new ArrayList<>();
        privilegeRepository.findAll().forEach(privileges::add);
        return privileges;
    }

    @Override
    public Privilege getById(Long id) {
        return privilegeRepository.findById(id).orElse(null);
    }

    @Override
    public Privilege getByName(String name) {
        return privilegeRepository.findByName(name);
    }
}
