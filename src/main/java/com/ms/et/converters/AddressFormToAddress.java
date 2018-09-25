package com.ms.et.converters;

import com.ms.et.commands.AddressForm;
import com.ms.et.domain.Address;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AddressFormToAddress implements Converter<AddressForm, Address> {
    @Override
    public Address convert(AddressForm addressForm) {
        Address address = new Address();
        if (addressForm.getId() != null && !StringUtils.isEmpty(addressForm.getId())) {
            address.setId(new Long(addressForm.getId()));
        }
        address.setCountry(addressForm.getCountry());
        address.setCity(addressForm.getCity());
        address.setPostalCode(addressForm.getPostalCode());
        address.setStreet(addressForm.getStreet());
        address.setNumber(addressForm.getNumber());
        return address;
    }
}
