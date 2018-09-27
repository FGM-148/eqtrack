package com.ms.et.services;

import com.ms.et.commands.ProjectForm;
import com.ms.et.domain.Project;

import java.util.List;

public interface ProjectService {
    List<Project> listAll();
    Project getById(Long id);
    Project saveOrUpdate(Project project);
    void delete(Long id);
    Project saveOrUpdateProjectForm(ProjectForm projectForm);
}