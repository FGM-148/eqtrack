package com.ms.et.services;

import com.ms.et.commands.CompanyForm;
import com.ms.et.domain.Company;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Override
    public List<Company> listAll() {
        return null;
    }

    @Override
    public Company getById(Long id) {
        return null;
    }

    @Override
    public Company saveOrUpdate(Company company) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Company saveOrUpdateCompanyForm(CompanyForm companyForm) {
        return null;
    }
}
