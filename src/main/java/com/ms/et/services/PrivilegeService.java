package com.ms.et.services;

import com.ms.et.domain.Privilege;

import java.util.List;

public interface PrivilegeService {
    List<Privilege> listAll();
    Privilege getById(Long id);
    Privilege getByName(String name);
}