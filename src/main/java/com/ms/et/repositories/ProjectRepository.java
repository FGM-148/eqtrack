package com.ms.et.repositories;

import com.ms.et.domain.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {
}
