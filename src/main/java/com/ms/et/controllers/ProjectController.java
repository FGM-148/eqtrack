package com.ms.et.controllers;

import com.ms.et.commands.ProjectForm;
import com.ms.et.converters.ProjectToProjectForm;
import com.ms.et.domain.Project;
import com.ms.et.services.CompanyService;
import com.ms.et.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class ProjectController {

    private ProjectService projectService;
    private ProjectToProjectForm projectToProjectForm;
    @Autowired
    private CompanyService companyService;

    @Autowired
    public void setProjectToProjectForm(ProjectToProjectForm projectToProjectForm) {
        this.projectToProjectForm = projectToProjectForm;
    }

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping({"/project/list", "/project"})
    public String listProjects(Model model) {
        model.addAttribute("projects", projectService.listAll());
        return "project/list";
    }

    @RequestMapping("/project/show/{id}")
    public String getProject(@PathVariable String id, Model model) {
        Project project = projectService.getById(Long.valueOf(id));
        model.addAttribute("project", project);
        return "project/show";
    }

    @RequestMapping("project/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Project project = projectService.getById(Long.valueOf(id));
        ProjectForm projectForm = projectToProjectForm.convert(project);

        model.addAttribute("projectForm", projectForm);
        model.addAttribute("allCompanys", companyService.listAll());
        return "project/projectform";
    }

    @RequestMapping("/project/new")
    public String newProject(Model model) {
        model.addAttribute("projectForm", new ProjectForm());
        model.addAttribute("allCompanys", companyService.listAll());
        return "project/projectform";
    }

    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public String saveOrUpdateProject(@Valid ProjectForm projectForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "project/projectform";
        }

        Project savedProject = projectService.saveOrUpdateProjectForm(projectForm);
//        return "redirect:/project/show/" + savedProject.getId();
        return "redirect:/project/list";
    }

    @RequestMapping("project/delete/{id}")
    public String delete(@PathVariable String id) {
        projectService.delete(Long.valueOf(id));
        return "redirect:/project/list";
    }
}
