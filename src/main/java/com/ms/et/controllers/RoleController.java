package com.ms.et.controllers;

import com.ms.et.commands.RoleForm;
import com.ms.et.converters.RoleToRoleForm;
import com.ms.et.domain.Role;
import com.ms.et.services.PrivilegeService;
import com.ms.et.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class RoleController {

    private RoleService roleService;
    private RoleToRoleForm roleToRoleForm;
    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    public void setRoleToRoleForm(RoleToRoleForm roleToRoleForm) {
        this.roleToRoleForm = roleToRoleForm;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping({"/role/list", "/role"})
    public String listRoles(Model model) {
        model.addAttribute("roles", roleService.listAll());
        return "role/list";
    }

    @RequestMapping("/role/show/{id}")
    public String getRole(@PathVariable String id, Model model) {
        Role role = roleService.getById(Long.valueOf(id));
        model.addAttribute("role", role);
        return "role/show";
    }

    @RequestMapping("role/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Role role = roleService.getById(Long.valueOf(id));
        RoleForm roleForm = roleToRoleForm.convert(role);

        model.addAttribute("roleForm", roleForm);
        model.addAttribute("allPrivileges", privilegeService.listAll());
        return "role/roleform";
    }

    @RequestMapping("/role/new")
    public String newRole(Model model) {
        model.addAttribute("roleForm", new RoleForm());
        model.addAttribute("allPrivileges", privilegeService.listAll());
        return "role/roleform";
    }

    @RequestMapping(value = "/role", method = RequestMethod.POST)
    public String saveOrUpdateRole(@Valid RoleForm roleForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "role/roleform";
        }

        Role savedRole = roleService.saveOrUpdateRoleForm(roleForm);
//        return "redirect:/role/show/" + savedRole.getId();
        return "redirect:/role/list";
    }

    @RequestMapping("role/delete/{id}")
    public String delete(@PathVariable String id) {
        roleService.delete(Long.valueOf(id));
        return "redirect:/role/list";
    }
}
