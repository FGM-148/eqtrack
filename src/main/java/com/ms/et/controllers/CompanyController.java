package com.ms.et.controllers;

import com.ms.et.commands.CompanyForm;
import com.ms.et.converters.CompanyToCompanyForm;
import com.ms.et.domain.Company;
import com.ms.et.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class CompanyController {

    private CompanyService companyService;
    private CompanyToCompanyForm companyToCompanyForm;

    @Autowired
    public void setCompanyToCompanyForm(CompanyToCompanyForm companyToCompanyForm) {
        this.companyToCompanyForm = companyToCompanyForm;
    }

    @Autowired
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RequestMapping({"/company/list", "/company"})
    public String listCompanys(Model model) {
        model.addAttribute("companys", companyService.listAll());
        return "company/list";
    }

    @RequestMapping("/company/show/{id}")
    public String getCompany(@PathVariable String id, Model model) {
        Company company = companyService.getById(Long.valueOf(id));
        model.addAttribute("company", company);
        return "company/show";
    }

    @RequestMapping("company/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Company company = companyService.getById(Long.valueOf(id));
        CompanyForm companyForm = companyToCompanyForm.convert(company);

        model.addAttribute("companyForm", companyForm);
        return "company/companyform";
    }

    @RequestMapping("/company/new")
    public String newCompany(Model model) {
        model.addAttribute("companyForm", new CompanyForm());
        return "company/companyform";
    }

    @RequestMapping(value = "/company", method = RequestMethod.POST)
    public String saveOrUpdateCompany(@Valid CompanyForm companyForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "company/companyform";
        }

        Company savedCompany = companyService.saveOrUpdateCompanyForm(companyForm);
//        return "redirect:/company/show/" + savedCompany.getId();
        return "redirect:/company/list";
    }

    @RequestMapping("company/delete/{id}")
    public String delete(@PathVariable String id) {
        companyService.delete(Long.valueOf(id));
        return "redirect:/company/list";
    }
}
