package com.ms.et.controllers;

import com.ms.et.commands.UserForm;
import com.ms.et.converters.UserToUserForm;
import com.ms.et.domain.User;
import com.ms.et.services.RoleService;
import com.ms.et.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class UserController {
    private UserService userService;
    private RoleService roleService;
    private UserToUserForm userToUserForm;

    @Autowired
    public void setUserToUserForm(UserToUserForm _userToUserForm) {
        userToUserForm = _userToUserForm;
    }

    @Autowired
    public void setUserService(UserService _userService) {
        userService = _userService;
    }

    @Autowired
    public void setRoleService(RoleService _roleService) {
        roleService = _roleService;
    }

    @RequestMapping({"/user/list", "user"})
    public String listItems(Model model) {
        model.addAttribute("users", userService.listAll());
        return "user/list";
    }

    @RequestMapping("/user/show/{id}")
    public String getUser(@PathVariable String id, Model model) {
        model.addAttribute("user", userService.getById(Long.valueOf(id)));
        return "user/show";
    }

    @RequestMapping("user/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        User user = userService.getById(Long.valueOf(id));
        UserForm userForm = userToUserForm.convert(user);

        model.addAttribute("userForm", userForm);
        return "user/userform";
    }

    @RequestMapping("/user/new")
    public String newUser(Model model) {
        model.addAttribute("userForm", new UserForm());
        model.addAttribute("allRoles", roleService.listAll());
        return "user/userform";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String saveOrUpdateUser(@Valid UserForm userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/userform";
        }

        User savedUser = userService.saveOrUpdateUserForm(userForm);
        return "redirect:/user/show/" + savedUser.getId();
    }

    @RequestMapping("user/delete/{id}")
    public String delete(@PathVariable String id) {
        userService.delete(Long.valueOf(id));
        return "redirect:/user/list";
    }
}