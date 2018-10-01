package com.ms.et.services;

import com.ms.et.domain.Archive;
import com.ms.et.repositories.ArchiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArchiveServiceImpl implements ArchiveService {
    @Autowired
    private ArchiveRepository archiveRepository;

    @Override
    public List<Archive> listAll() {
        List<Archive> archives = new ArrayList<>();
        archiveRepository.findAll().forEach(archives::add);
        return archives;
    }

    @Override
    public Archive getById(Long id) {
        return archiveRepository.findById(id).orElse(null);
    }
}
