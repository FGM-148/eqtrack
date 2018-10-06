package com.ms.et.services;

import com.ms.et.commands.ChooseProjectForm;
import com.ms.et.commands.ItemForm;
import com.ms.et.converters.ItemFormToItem;
import com.ms.et.domain.*;
import com.ms.et.repositories.ArchiveRepository;
import com.ms.et.repositories.ItemChangeLogRepository;
import com.ms.et.repositories.ItemRepository;
import com.ms.et.services.specification.ItemSpecification;
import com.ms.et.services.specification.SearchOperation;
import com.ms.et.services.specification.SpecSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
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
    @Transactional
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
    @Transactional
    public Item saveOrUpdateItemForm(ItemForm itemForm) {
        Item savedItem = saveOrUpdate(mItemFormToItem.convert(itemForm));
        System.out.println("Saved Item Id: " + savedItem.getId());
        return savedItem;
    }

    @Override
    public Page<Item> listAllByPage(Pageable pageable) {
            return itemRepository.findAll(pageable);
    }

    @Override
    public Page<Item> listAllStorageByPage(Pageable pageable) {
        ItemSpecification spec4 =
                new ItemSpecification(new SpecSearchCriteria("inStorage", SearchOperation.EQUALITY, true));
        return  itemRepository.findAll(Specification.where(spec4), pageable);
    }

    @Override
    public List<Item> fuzzySearchAll(String q) {
        ItemSpecification spec1 =
                new ItemSpecification(new SpecSearchCriteria("name", SearchOperation.CONTAINS, q));
        ItemSpecification spec2 =
                new ItemSpecification(new SpecSearchCriteria("serialNumber", SearchOperation.CONTAINS, q));
        ItemSpecification spec3 =
                new ItemSpecification(new SpecSearchCriteria("internalNumber", SearchOperation.CONTAINS, q));

        List<Item> results = itemRepository.findAll(Specifications.where(spec1).or(spec2).or(spec3));
        return results;
    }

    @Override
    public List<Item> fuzzySearchInStorage(String q, boolean inStorage) {
        ItemSpecification spec1 =
                new ItemSpecification(new SpecSearchCriteria("name", SearchOperation.CONTAINS, q));
        ItemSpecification spec2 =
                new ItemSpecification(new SpecSearchCriteria("serialNumber", SearchOperation.CONTAINS, q));
        ItemSpecification spec3 =
                new ItemSpecification(new SpecSearchCriteria("internalNumber", SearchOperation.CONTAINS, q));
        ItemSpecification spec4 =
                new ItemSpecification(new SpecSearchCriteria("inStorage", SearchOperation.EQUALITY, inStorage));

        List<Item> results = itemRepository.findAll(Specifications.where(spec1).or(spec2).or(spec3).and(spec4));
        return results;
    }

    @Override
    @Transactional
    public void assignItemToUser(Item item, User user) {
        item.setUser(user);
        item.setInStorage(false);
        ItemChangeLog itemChangeLog = new ItemChangeLog();
        itemChangeLog.setEventDate(new java.util.Date(System.currentTimeMillis()));
        itemChangeLog.setItem(item);
        itemChangeLog.setEvent("Assigned to: " + user.toString());
        itemChangeLogRepository.save(itemChangeLog);
        itemRepository.save(item);
    }

    @Override
    @Transactional
    public void returnToIct(Item item) {
        item.setUser(null);
        item.setProject(null);
        item.setInStorage(true);
        ItemChangeLog itemChangeLog = new ItemChangeLog();
        itemChangeLog.setEventDate(new java.util.Date(System.currentTimeMillis()));
        itemChangeLog.setItem(item);
        itemChangeLog.setEvent("Returned to storage");
        itemChangeLogRepository.save(itemChangeLog);
        itemRepository.save(item);
    }

    @Override
    @Transactional
    public void assignItemToProject(ChooseProjectForm chooseProjectForm) {
        Long itemId = chooseProjectForm.getItemId();
        Project project = chooseProjectForm.getProject();
        System.out.println("ChooseProject: itemId: " + itemId);
        System.out.println("ChooseProject: project name: " + project.getName());
        Item item = getById(itemId);
        item.setProject(project);
        item.setInStorage(false);
        ItemChangeLog itemChangeLog = new ItemChangeLog();
        itemChangeLog.setEventDate(new java.util.Date(System.currentTimeMillis()));
        itemChangeLog.setItem(item);
        itemChangeLog.setEvent("Assigned to project " + project.getName());
        itemChangeLogRepository.save(itemChangeLog);
        itemRepository.save(item);
    }

    @Override
    public List<Item> findAllByProject(Project project) {
        return itemRepository.findAllByProject(project);
    }
}
