package com.ms.et.services;

import com.ms.et.commands.ChooseProjectForm;
import com.ms.et.commands.ItemForm;
import com.ms.et.domain.Item;
import com.ms.et.domain.Project;
import com.ms.et.domain.User;
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

    Page<Item> listAllStorageByPage(Pageable pageable);

    List<Item> fuzzySearchAll(String q);
    List<Item> fuzzySearchInStorage(String q, boolean inStorage);

    void assignItemToUser(Item item, User user);

    void returnToIct(Item item);

    void assignItemToProject(ChooseProjectForm chooseProjectForm);

    List<Item> findAllByProject(Project project);
}