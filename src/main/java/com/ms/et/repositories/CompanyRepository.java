package com.ms.et.repositories;

import com.ms.et.domain.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, Long> {
}
