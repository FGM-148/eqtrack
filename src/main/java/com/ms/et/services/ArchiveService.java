package com.ms.et.services;

import com.ms.et.domain.Archive;

import java.util.List;

public interface ArchiveService {
    List<Archive> listAll();
    Archive getById(Long id);
}
