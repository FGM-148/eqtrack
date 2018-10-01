package com.ms.et.services;

import com.ms.et.commands.ItemForm;
import com.ms.et.converters.ItemFormToItem;
import com.ms.et.domain.Archive;
import com.ms.et.domain.Item;
import com.ms.et.domain.ItemChangeLog;
import com.ms.et.repositories.ArchiveRepository;
import com.ms.et.repositories.ItemChangeLogRepository;
import com.ms.et.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ItemServiceImpl implements ItemService{

    private ItemRepository itemRepository;
    private ItemFormToItem mItemFormToItem;
    private ItemChangeLogRepository itemChangeLogRepository;
    private AddressService addressService;
    @Autowired
    private ArchiveRepository archiveRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository,
                           ItemFormToItem itemFormToItem,
                           ItemChangeLogRepository itemChangeLogRepository,
                           AddressService addressService) {
        this.itemRepository = itemRepository;
        mItemFormToItem = itemFormToItem;
        this.itemChangeLogRepository = itemChangeLogRepository;
        this.addressService = addressService;
    }

    @Override
    public List<Item> listAll() {
        List<Item> items = new ArrayList();
        itemRepository.findAll().forEach(items::add);
        return items;
    }

    @Override
    public Item getById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    @Override
    public Item saveOrUpdate(Item item) {
        boolean newItem = false;
        if (item.getId() == null) {
            newItem = true;
        }
        addressService.saveOrUpdateItemAddress(item);
        itemRepository.save(item);
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
        Archive archive = new Archive();
        Item item = itemRepository.findById(id).orElse(null);
        if (item == null) return;
        archive.setName(item.getName());
        archive.setInternalNumber(item.getInternalNumber());
        archive.setSerialNumber(item.getSerialNumber());
        archive.setSourceOfDelivery(item.getSourceOfDelivery());
        archive.setDeliveryDate(item.getDeliveryDate());
        java.util.Date timeStamp = new java.util.Date(System.currentTimeMillis());
        archive.setAchriveDate(timeStamp);
        Set<ItemChangeLog> changeLogs = new HashSet<ItemChangeLog>();
        for(ItemChangeLog log : item.getItemChangeLogs()) {
            ItemChangeLog archivedLog = new ItemChangeLog();
            archivedLog.setEvent(log.getEvent());
            archivedLog.setEventDate(log.getEventDate());
            changeLogs.add(archivedLog);
        }
        archive.setArchivedChangeLogs(changeLogs);
        itemRepository.deleteById(id);
        archiveRepository.save(archive);
    }

    @Override
    public Item saveOrUpdateItemForm(ItemForm itemForm) {
        Item savedItem = saveOrUpdate(mItemFormToItem.convert(itemForm));
        System.out.println("Saved Item Id: " + savedItem.getId());
        return savedItem;
    }

    @Override
    public Page<Item> listAllByPage(Pageable pageable) {
            return itemRepository.findAll(pageable);
    }
}
