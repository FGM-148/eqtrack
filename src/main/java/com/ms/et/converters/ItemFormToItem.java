package com.ms.et.converters;

import com.ms.et.commands.ItemForm;
import com.ms.et.domain.Item;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

@Component
public class ItemFormToItem implements Converter<ItemForm, Item> {
    @Override
    public Item convert(ItemForm itemForm) {
        Item item =  new Item();
        if (itemForm.getId() != null && !StringUtils.isEmpty(itemForm.getId())) {
            item.setId(new Long(itemForm.getId()));
        }
        item.setSourceOfDelivery(itemForm.getSourceOfDelivery());
        item.setDeliveryDate(itemForm.getDeliveryDate());
        item.setInternalNumber(itemForm.getInternalNumber());
        item.setSerialNumber(itemForm.getSerialNumber());
        item.setName(itemForm.getName());
        return item;
    }
}
