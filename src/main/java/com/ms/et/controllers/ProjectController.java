package com.ms.et.controllers;

import com.ms.et.commands.ProjectForm;
import com.ms.et.converters.ProjectToProjectForm;
import com.ms.et.domain.Project;
import com.ms.et.domain.User;
import com.ms.et.services.CompanyService;
import com.ms.et.services.ItemService;
import com.ms.et.services.ProjectService;
import com.ms.et.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class ProjectController {

    private ProjectService projectService;
    private ProjectToProjectForm projectToProjectForm;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;

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
    public String getProject(@PathVariable String id, Model model, HttpServletRequest request) {
        Project project = projectService.getById(Long.valueOf(id));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User secUser = (User) auth.getPrincipal();
        User user = userService.getById(secUser.getId());
        Set<Project> userProjects = user.getProjects();
        Boolean isOwner = false;
        for (Project i : userProjects) {
            if(i.getId().toString().equals(id)) {
                isOwner = true;
                break;
            }
        }
        String assignLink = "/project/assignToUser/" + id;
        String referer = request.getHeader("Referer");
        model.addAttribute("isOwner", isOwner.toString());
        model.addAttribute("referer", referer);
        model.addAttribute("assignLink", assignLink);
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

    @RequestMapping("project/showAssigned")
    public String showAssignedProjects(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User secUser = (User) auth.getPrincipal();
        User user = userService.getById(secUser.getId());
        Set<Project> userProjects = user.getProjects();
        List<Project> myProjects = new ArrayList();
        userProjects.forEach(myProjects::add);
        model.addAttribute("projects", myProjects);
        return "project/list";
    }

    @RequestMapping("/project/assignToUser/{id}")
    public String assignProjectToUser(@PathVariable String id, Model model, HttpServletRequest request) {
        System.out.println("trying to assign...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Project project = projectService.getById(new Long(id));
        projectService.assignProjectToUser(project, user);
        String referer = request.getHeader("Referer");
        return "redirect:/project/showAssigned";
    }

    @RequestMapping("/project/showItems/{id}")
    public String showItems(@PathVariable String id, Model model) {
        Project project = projectService.getById(new Long (id));
        model.addAttribute("items", itemService.findAllByProject(project));
        return "item/list";
    }
}
