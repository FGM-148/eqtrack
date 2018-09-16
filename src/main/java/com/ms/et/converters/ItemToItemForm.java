package com.ms.et.converters;

import com.ms.et.commands.ItemForm;
import com.ms.et.domain.Item;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ItemToItemForm implements Converter<Item, ItemForm> {
    @Override
    public ItemForm convert(Item item) {
        ItemForm itemForm = new ItemForm();
        itemForm.setId(item.getId());
        itemForm.setSourceOfDelivery(item.getSourceOfDelivery());
        itemForm.setDeliveryDate(item.getDeliveryDate());
        itemForm.setInternalNumber(item.getInternalNumber());
        itemForm.setSerialNumber(item.getSerialNumber());
        itemForm.setName(item.getName());
        return itemForm;
    }
}
