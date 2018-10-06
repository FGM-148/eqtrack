package com.ms.et.converters;

import com.ms.et.commands.ItemForm;
import com.ms.et.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ItemToItemForm implements Converter<Item, ItemForm> {
    @Autowired
    AddressToAddressForm addressToAddressForm;

    @Override
    public ItemForm convert(Item item) {
        ItemForm itemForm = new ItemForm();
        itemForm.setId(item.getId());
        itemForm.setSourceOfDelivery(addressToAddressForm.convert(item.getSourceOfDelivery()));
        itemForm.setDeliveryDate(item.getDeliveryDate());
        itemForm.setInternalNumber(item.getInternalNumber());
        itemForm.setSerialNumber(item.getSerialNumber());
        itemForm.setName(item.getName());
        itemForm.setCompany(item.getCompany());
        itemForm.setInStorage(item.isInStorage());
        itemForm.setProject(item.getProject());
        itemForm.setUser(item.getUser());
        return itemForm;
    }
}
