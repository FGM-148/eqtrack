package com.ms.et.services;

import com.ms.et.domain.ItemChangeLog;
import com.ms.et.repositories.ItemChangeLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemChangeLogServiceImpl implements ItemChangeLogService {
    @Autowired
    ItemChangeLogRepository itemChangeLogRepository;

    @Override
    public List<ItemChangeLog> listAll() {
        List<ItemChangeLog> logs = new ArrayList<>();
        itemChangeLogRepository.findAll().forEach(logs::add);
        return logs;
    }

    @Override
    public ItemChangeLog getById(Long id) {
        return null;
    }

    @Override
    public ItemChangeLog saveOrUpdate(ItemChangeLog itemChangeLog) {
        itemChangeLogRepository.save(itemChangeLog);
        return itemChangeLog;
    }

    @Override
    public void delete(Long id) {
        itemChangeLogRepository.deleteById(id);
    }
}
