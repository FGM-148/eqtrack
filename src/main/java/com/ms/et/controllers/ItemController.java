package com.ms.et.controllers;

import com.ms.et.commands.ChooseProjectForm;
import com.ms.et.commands.ItemForm;
import com.ms.et.converters.ItemToItemForm;
import com.ms.et.domain.Item;
import com.ms.et.domain.ItemChangeLog;
import com.ms.et.domain.Project;
import com.ms.et.domain.User;
import com.ms.et.services.CompanyService;
import com.ms.et.services.ItemService;
import com.ms.et.services.ProjectService;
import com.ms.et.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.Binding;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ItemController {

    private ItemService itemService;
    private ItemToItemForm itemToItemForm;

    private static int currentPage = 1;
    private static int pageSize = 6;

    private static int currentStoragePage = 1;
    private static int pageStorageSize = 6;

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    public void setItemToItemForm(ItemToItemForm itemToItemForm) {
        this.itemToItemForm = itemToItemForm;
    }

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping(value = "/item/search", method = RequestMethod.GET)
    public String search(@RequestParam(value = "search", required = false) String q, Model model) {
        List<Item> searchResults = null;
        try {
            searchResults = itemService.fuzzySearchAll(q);

        } catch (Exception ex) {
            return "redirect:/exception";
        }
        model.addAttribute("search", searchResults);
        return "item/search";

    }

    @RequestMapping({"/item/list", "/item"})
    public String listItemss(Model model) {
        model.addAttribute("items", itemService.listAll());
        return "item/listItems";
    }

    @RequestMapping(value = "/item/listItems", method = RequestMethod.GET)
    public String listItems(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        page.ifPresent(p -> currentPage = p);
        size.ifPresent(s -> pageSize = s);

        Page<Item> itemPage = itemService.listAllByPage(PageRequest.of(currentPage - 1, pageSize));


        model.addAttribute("itemPage", itemPage);

        int totalPages = itemPage.getTotalPages();
        System.out.println("total pages = " + totalPages);
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "item/listItems";

    }

    @RequestMapping("/item/show/{id}")
    public String getItem(@PathVariable String id, Model model, HttpServletRequest request) {
        Item item = itemService.getById(Long.valueOf(id));
        List<ItemChangeLog> itemChangeLogs = new ArrayList<>();
        item.getItemChangeLogs().forEach(itemChangeLogs::add);
        Collections.sort(itemChangeLogs);
        model.addAttribute("item", item);
        model.addAttribute("logs", itemChangeLogs);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User secUser = (User) auth.getPrincipal();
        User user = userService.getById(secUser.getId());
        Set<Item> userItems = user.getItems();
        Boolean isOwner = false;
        Boolean inStorage = item.isInStorage();
        for (Item i : userItems) {
            if(i.getId().toString().equals(id)) {
                isOwner = true;
                break;
            }
        }
        String assignLink = "/item/assignToUser/" + id;
        String storageLink = "/item/returnToIct/" + id;
        String projectAssignLink = "/item/newProjectAssign/" + id;
        String referer = request.getHeader("Referer");
        model.addAttribute("projectAssignLink", projectAssignLink);
        model.addAttribute("isOwner", isOwner.toString());
        model.addAttribute("assignLink", assignLink);
        model.addAttribute("referer", referer);
        model.addAttribute("storageLink", storageLink);
        model.addAttribute("inStorage", inStorage.toString());
        return "item/show";
    }

    @RequestMapping("/item/showAssigned")
    public String showAssignedItems(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User secUser = (User) auth.getPrincipal();
        User user = userService.getById(secUser.getId());
        Set<Item> userItems = user.getItems();
        List<Item> myItems = new ArrayList();
        userItems.forEach(myItems::add);
        model.addAttribute("items", myItems);
        return "item/list";
    }

    @RequestMapping(value = "/item/storageItems", method = RequestMethod.GET)
    public String showStorage(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        page.ifPresent(p -> currentStoragePage = p);
        size.ifPresent(s -> pageStorageSize = s);

        Page<Item> itemPage = itemService.listAllStorageByPage(PageRequest.of(currentStoragePage - 1, pageStorageSize));

        model.addAttribute("itemPage", itemPage);

        int totalPages = itemPage.getTotalPages();
        System.out.println("total pages = " + totalPages);
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "item/storageItems";

    }

    @RequestMapping("/item/assignToUser/{id}")
    public String assignItemToUser(@PathVariable String id, Model model, HttpServletRequest request) {
        System.out.println("trying to assign...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Item item = itemService.getById(new Long(id));
        itemService.assignItemToUser(item, user);
        String referer = request.getHeader("Referer");
        return "redirect:/item/showAssigned";
    }

    @RequestMapping(value = "/item/assignToProject", method = RequestMethod.POST)
    public String assignItemToProject(@Valid ChooseProjectForm chooseProjectForm, Model model, BindingResult bindingResult, HttpServletRequest request) {
        System.out.println("trying to assign item to project...");
        itemService.assignItemToProject(chooseProjectForm);
            if (bindingResult.hasErrors()) {
                return "project/projectform";
            }

        return "item/assignedToProject";
    }


    @RequestMapping("/item/returnToIct/{id}")
    public String returnToIct(@PathVariable String id, Model model, HttpServletRequest request) {
        System.out.println("trying to return...");
        Item item = itemService.getById(new Long(id));
        itemService.returnToIct(item);
        String referer = request.getHeader("Referer");
        return "redirect:/item/returnedToStorage";
    }

    @RequestMapping("item/returnedToStorage")
    public String returnedToStorage() {
        return "item/returnedToStorage";
    }

    @RequestMapping("item/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Item item = itemService.getById(Long.valueOf(id));
        ItemForm itemForm = itemToItemForm.convert(item);
        model.addAttribute("allCompanys", companyService.listAll());

        model.addAttribute("itemForm", itemForm);
        return "item/itemform";
    }

    @RequestMapping("/item/newProjectAssign/{id}")
    public String newProject(@PathVariable String id, Model model, HttpServletRequest request) {
        ChooseProjectForm chooseProjectForm = new ChooseProjectForm();
        chooseProjectForm.setItemId(new Long (id));
        model.addAttribute("chooseProjectForm", chooseProjectForm);
        model.addAttribute("allProjects", projectService.listAll());
        model.addAttribute("referer", request.getHeader("Referer"));
        return "item/chooseProject";
    }

    @RequestMapping("/item/new")
    public String newItem(Model model) {
        model.addAttribute("itemForm", new ItemForm());
        model.addAttribute("allCompanys", companyService.listAll());
        return "item/itemform";
    }

    @RequestMapping(value = "/item", method = RequestMethod.POST)
    public String saveOrUpdateItem(@Valid ItemForm itemForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "item/itemform";
        }

        Item savedItem = itemService.saveOrUpdateItemForm(itemForm);
        return "redirect:/item/show/" + savedItem.getId();
    }

    @RequestMapping("item/delete/{id}")
    public String delete(@PathVariable String id) {
        itemService.delete(Long.valueOf(id));
        return "redirect:/archive/list";
    }
}
