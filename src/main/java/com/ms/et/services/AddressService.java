package com.ms.et.services;

import com.ms.et.domain.Company;
import com.ms.et.domain.Item;

public interface AddressService {
//    List<Address> listAll();
//    Address getById(Long id);
    Item saveOrUpdateItemAddress(Item item);
    Company saveOrUpdateCompanyAddress(Company company);
//    void delete(Long id);
//    Address saveOrUpdateAddressForm(AddressForm addressForm);
}
