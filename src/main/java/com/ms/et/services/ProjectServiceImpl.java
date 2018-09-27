package com.ms.et.services;

import com.ms.et.commands.ProjectForm;
import com.ms.et.converters.ProjectFormToProject;
import com.ms.et.domain.Project;
import com.ms.et.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectFormToProject projectFormToProject;

    @Override
    public List<Project> listAll() {
        List<Project> projects = new ArrayList<>();
        projectRepository.findAll().forEach(projects::add);
        return projects;
    }

    @Override
    public Project getById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    @Override
    public Project saveOrUpdate(Project project) {
        projectRepository.save(project);
        return project;
    }

    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public Project saveOrUpdateProjectForm(ProjectForm projectForm) {
        return saveOrUpdate(projectFormToProject.convert(projectForm));
    }
}
