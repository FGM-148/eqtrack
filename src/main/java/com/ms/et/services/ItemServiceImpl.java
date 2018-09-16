package com.ms.et.services;

import com.ms.et.commands.ItemForm;
import com.ms.et.converters.ItemFormToItem;
import com.ms.et.domain.Item;
import com.ms.et.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{

    private ItemRepository mItemRepository;
    private ItemFormToItem mItemFormToItem;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ItemFormToItem itemFormToItem) {
        mItemRepository = itemRepository;
        mItemFormToItem = itemFormToItem;
    }

    @Override
    public List<Item> listAll() {
        List<Item> items = new ArrayList();
        mItemRepository.findAll().forEach(items::add);
        return items;
    }

    @Override
    public Item getById(Long id) {
        return mItemRepository.findById(id).orElse(null);
    }

    @Override
    public Item saveOrUpdate(Item item) {
        mItemRepository.save(item);
        return item;
    }

    @Override
    public void delete(Long id) {
        mItemRepository.deleteById(id);
    }

    @Override
    public Item saveOrUpdateItemForm(ItemForm itemForm) {
        Item savedItem = saveOrUpdate(mItemFormToItem.convert(itemForm));
        System.out.println("Saved Item Id: " + savedItem.getId());
        return savedItem;
    }
}
