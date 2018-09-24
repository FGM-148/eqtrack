package com.ms.et.services;

import com.ms.et.domain.Item;
import com.ms.et.domain.ItemChangeLog;

import java.util.List;

public interface ItemChangeLogService {
    List<ItemChangeLog> listAll();
    ItemChangeLog getById(Long id);
    ItemChangeLog saveOrUpdate(ItemChangeLog itemChangeLog);
    void delete(Long id);
}
