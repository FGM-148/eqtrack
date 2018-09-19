package com.ms.et.controllers;

import com.ms.et.commands.ItemForm;
import com.ms.et.converters.ItemToItemForm;
import com.ms.et.domain.Item;
import com.ms.et.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class ItemController {

    private ItemService mItemService;
    private ItemToItemForm mItemToItemForm;

    @Autowired
    public void setItemToItemForm(ItemToItemForm itemToItemForm) {
        mItemToItemForm = itemToItemForm;
    }

    @Autowired
    public void setItemService(ItemService itemService) {
        mItemService = itemService;
    }

    @RequestMapping({"/item/list", "/item"})
    public String listItems(Model model) {
        model.addAttribute("items", mItemService.listAll());
        return "item/list";
    }

    @RequestMapping("/item/show/{id}")
    public String getItem(@PathVariable String id, Model model) {
        model.addAttribute("item", mItemService.getById(Long.valueOf(id)));
        return "item/show";
    }

    @RequestMapping("item/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Item item = mItemService.getById(Long.valueOf(id));
        ItemForm itemForm = mItemToItemForm.convert(item);

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

        Item savedItem = mItemService.saveOrUpdateItemForm(itemForm);
        return "redirect:/item/show/" + savedItem.getId();
    }

    @RequestMapping("item/delete/{id}")
    public String delete(@PathVariable String id) {
        mItemService.delete(Long.valueOf(id));
        return "redirect:/item/list";
    }
}
