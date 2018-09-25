package com.ms.et.services;

import com.ms.et.commands.ItemForm;
import com.ms.et.converters.ItemFormToItem;
import com.ms.et.domain.Address;
import com.ms.et.domain.Item;
import com.ms.et.domain.ItemChangeLog;
import com.ms.et.repositories.AddressRepository;
import com.ms.et.repositories.ItemChangeLogRepository;
import com.ms.et.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{

    private ItemRepository mItemRepository;
    private ItemFormToItem mItemFormToItem;
    private ItemChangeLogRepository itemChangeLogRepository;
    private AddressRepository addressRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository,
                           ItemFormToItem itemFormToItem,
                           ItemChangeLogRepository itemChangeLogRepository,
                           AddressRepository addressRepository) {
        mItemRepository = itemRepository;
        mItemFormToItem = itemFormToItem;
        this.itemChangeLogRepository = itemChangeLogRepository;
        this.addressRepository = addressRepository;
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
        boolean newItem = false;
        boolean addressExists = false;
        if (item.getId() == null) {
            newItem = true;
        }
        Address address = item.getSourceOfDelivery();
        Iterable<Address> allAddress = addressRepository.findAll();
        for (Address a : allAddress) {
            if (a.equals(address)) {
                addressExists = true;
                item.setSourceOfDelivery(a);
                break;
            }
        }
        if (!addressExists) {
            addressRepository.save(item.getSourceOfDelivery());
        }
        mItemRepository.save(item);
        ItemChangeLog itemChangeLog = new ItemChangeLog();
        itemChangeLog.setEventDate(new java.util.Date(System.currentTimeMillis()));
        itemChangeLog.setItem(item);
        if (newItem) {
            itemChangeLog.setEvent("Created");
        }
        else {
            itemChangeLog.setEvent("Edited");
        }
        itemChangeLogRepository.save(itemChangeLog);
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
