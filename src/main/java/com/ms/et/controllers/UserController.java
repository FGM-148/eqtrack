package com.ms.et.controllers;

import com.ms.et.commands.UserForm;
import com.ms.et.converters.UserToUserForm;
import com.ms.et.domain.Item;
import com.ms.et.domain.User;
import com.ms.et.services.RoleService;
import com.ms.et.services.UserService;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @RequestMapping(value = "/user/search", method = RequestMethod.GET)
    public String search(@RequestParam(value = "search", required = false) String q, Model model) {
        List<User> searchResults = null;
        try {
            searchResults = userService.fuzzySearch(q);

        } catch (Exception ex) {
            return "redirect:/exception";
        }
        model.addAttribute("search", searchResults);
        return "user/search";

    }

    @RequestMapping({"/user/list", "user"})
    public String listItems(Model model) {
        model.addAttribute("users", userService.listAll());
        return "user/list";
    }

    @RequestMapping("/user/show/{id}")
    public String getUser(@PathVariable String id, Model model, HttpServletRequest request) {
        model.addAttribute("user", userService.getById(Long.valueOf(id)));
        List<Item> myItems = new ArrayList();
        User user = userService.getById(new Long(id));
        Set<Item> userItems = user.getItems();
        userItems.forEach(myItems::add);
        model.addAttribute("items", myItems);
        model.addAttribute("referer", request.getHeader("Referer"));
        return "user/show";
    }

    @RequestMapping("user/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        User user = userService.getById(Long.valueOf(id));
        UserForm userForm = userToUserForm.convert(user);

        model.addAttribute("userForm", userForm);
        model.addAttribute("allRoles", roleService.listAll());
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
        try {
            User savedUser = userService.saveOrUpdateUserForm(userForm);
        }
        catch (Exception e) {
            return "redirect:/exception";
        }
//        return "redirect:/user/show/" + savedUser.getId();
        return "redirect:/user/list";
    }

    @RequestMapping("user/delete/{id}")
    public String delete(@PathVariable String id) {
        userService.delete(Long.valueOf(id));
        return "redirect:/user/list";
    }
}
