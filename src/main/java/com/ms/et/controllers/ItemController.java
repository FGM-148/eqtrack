package com.ms.et.controllers;

import com.ms.et.commands.ItemForm;
import com.ms.et.converters.ItemToItemForm;
import com.ms.et.domain.Item;
import com.ms.et.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ItemController {

    private ItemService itemService;
    private ItemToItemForm itemToItemForm;

    private static int currentPage = 1;
    private static int pageSize = 5;

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
            // here you should handle unexpected errors
            // ...
            // throw ex;
        }
        model.addAttribute("search", searchResults);
        return "item/search";

    }

    @RequestMapping({"/item/list", "/item"})
    public String listItemss(Model model) {
        model.addAttribute("items", itemService.listAll());
        return "item/list";
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
    public String getItem(@PathVariable String id, Model model) {
        Item item = itemService.getById(Long.valueOf(id));
        model.addAttribute("item", item);
        model.addAttribute("logs", item.getItemChangeLogs());
        return "item/show";
    }

    @RequestMapping("item/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Item item = itemService.getById(Long.valueOf(id));
        ItemForm itemForm = itemToItemForm.convert(item);

        model.addAttribute("itemForm", itemForm);
        return "item/itemform";
    }

    @RequestMapping("/item/new")
    public String newItem(Model model) {
        model.addAttribute("itemForm", new ItemForm());
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
        return "redirect:/item/list";
    }
}
