package com.ms.et.services;

import com.ms.et.commands.ItemForm;
import com.ms.et.domain.Item;

import java.util.List;

public interface ItemService {

    List<Item> listAll();
    Item getById(Long id);
    Item saveOrUpdate(Item item);
    void delete(Long id);
    Item saveOrUpdateItemForm(ItemForm itemForm);
}
