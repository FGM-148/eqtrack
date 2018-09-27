package com.ms.et.converters;

import com.ms.et.commands.ProjectForm;
import com.ms.et.domain.Project;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProjectToProjectForm implements Converter<Project, ProjectForm> {
    @Override
    public ProjectForm convert(Project source) {
        ProjectForm projectForm = new ProjectForm();
        projectForm.setId(source.getId());
        projectForm.setName(source.getName());
        projectForm.setCompanyId(source.getCompany().getId());
        return projectForm;
    }
}
