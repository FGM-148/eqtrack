package com.ms.et.services;

import com.ms.et.commands.CompanyForm;
import com.ms.et.domain.Company;

import java.util.List;

public interface CompanyService {
    List<Company> listAll();
    Company getById(Long id);
    Company saveOrUpdate(Company company);
    void delete(Long id);
    Company saveOrUpdateCompanyForm(CompanyForm companyForm);
}
