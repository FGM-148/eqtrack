package com.ms.et.services;

import com.ms.et.commands.CompanyForm;
import com.ms.et.converters.CompanyFormToCompany;
import com.ms.et.domain.Company;
import com.ms.et.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private AddressService addressService;
    @Autowired
    private CompanyFormToCompany companyFormToCompany;

    @Override
    public List<Company> listAll() {
        List<Company> companies = new ArrayList<>();
        companyRepository.findAll().forEach(companies::add);
        return companies;
    }

    @Override
    public Company getById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Company saveOrUpdate(Company company) {
        addressService.saveOrUpdateCompanyAddress(company);
        companyRepository.save(company);
        return company;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        companyRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Company saveOrUpdateCompanyForm(CompanyForm companyForm) {
        Company savedCompany = saveOrUpdate(companyFormToCompany.convert(companyForm));
        return savedCompany;
    }
}
