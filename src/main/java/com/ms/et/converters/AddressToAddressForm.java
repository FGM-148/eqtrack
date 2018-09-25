package com.ms.et.converters;

import com.ms.et.commands.AddressForm;
import com.ms.et.domain.Address;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AddressToAddressForm implements Converter<Address, AddressForm> {
    @Override
    public AddressForm convert(Address address) {
        AddressForm addressForm = new AddressForm();
        addressForm.setId(address.getId());
        addressForm.setCountry(address.getCountry());
        addressForm.setCity(address.getCity());
        addressForm.setPostalCode(address.getPostalCode());
        addressForm.setStreet(address.getStreet());
        addressForm.setNumber(address.getNumber());
        return addressForm;
    }
}
