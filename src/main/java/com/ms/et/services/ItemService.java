package com.ms.et.services;

import com.ms.et.commands.ItemForm;
import com.ms.et.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    List<Item> listAll();
    Item getById(Long id);
    Item saveOrUpdate(Item item);
    void delete(Long id);
    Item saveOrUpdateItemForm(ItemForm itemForm);
    Page<Item> listAllByPage(Pageable pageable);
    List<Item> fuzzySearchAll(String q);
    List<Item> fuzzySearchInStorage(String q, boolean inStorage);
}