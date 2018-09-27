package com.ms.et.converters;

import com.ms.et.commands.ProjectForm;
import com.ms.et.domain.Project;
import com.ms.et.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProjectFormToProject implements Converter<ProjectForm, Project> {
    @Autowired
    private CompanyService companyService;

    @Override
    public Project convert(ProjectForm source) {
        Project project = new Project();
        project.setId(source.getId());
        project.setName(source.getName());
        project.setCompany(companyService.getById(source.getCompanyId()));
        return project;
    }
}
