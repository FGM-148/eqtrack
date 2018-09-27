package com.ms.et.services;

import com.ms.et.domain.Address;
import com.ms.et.domain.Company;
import com.ms.et.domain.Item;
import com.ms.et.repositories.AddressRepository;
import com.ms.et.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Item saveOrUpdateItemAddress(Item item) {
        Address address = item.getSourceOfDelivery();
        boolean addressExists = false;
        Iterable<Address> allAddress = addressRepository.findAll();
        for (Address a : allAddress) {
            if (a.equals(address)) {
                addressExists = true;
                item.setSourceOfDelivery(a);
                break;
            }
        }
        if (!addressExists) {
            addressRepository.save(item.getSourceOfDelivery());
        }
        return item;
    }

    @Override
    public Company saveOrUpdateCompanyAddress(Company company) {
        Address address = company.getAddress();
        boolean addressExists = false;
        Iterable<Address> allAddress = addressRepository.findAll();
        for (Address a : allAddress) {
            if (a.equals(address)) {
                addressExists = true;
                company.setAddress(a);
                break;
            }
        }
        if (!addressExists) {
            addressRepository.save(company.getAddress());
        }
        return company;
    }
}
