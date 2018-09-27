package com.ms.et.converters;

import com.ms.et.commands.CompanyForm;
import com.ms.et.domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CompanyFormToCompany implements Converter<CompanyForm, Company> {
    @Autowired
    AddressFormToAddress addressFormToAddress;

    @Override
    public Company convert(CompanyForm source) {
        Company company = new Company();
        company.setId(source.getId());
        company.setName(source.getName());
        company.setAddress(addressFormToAddress.convert(source.getAddressForm()));
        return company;
    }
}
