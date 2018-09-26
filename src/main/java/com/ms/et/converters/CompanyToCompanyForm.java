package com.ms.et.converters;

import com.ms.et.commands.CompanyForm;
import com.ms.et.domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class CompanyToCompanyForm implements Converter<Company, CompanyForm> {
    @Autowired
    AddressToAddressForm addressToAddressForm;

    @Override
    public CompanyForm convert(Company source) {
        CompanyForm companyForm = new CompanyForm();
        companyForm.setId(source.getId());
        companyForm.setName(source.getName());
        companyForm.setAddressForm(addressToAddressForm.convert(source.getAddress()));
        return companyForm;
    }
}
