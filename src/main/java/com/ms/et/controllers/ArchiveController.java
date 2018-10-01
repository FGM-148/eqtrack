package com.ms.et.controllers;

import com.ms.et.domain.Archive;
import com.ms.et.services.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ArchiveController {

    private ArchiveService archiveService;

    @Autowired
    public void setArchiveService(ArchiveService archiveService) {
        this.archiveService = archiveService;
    }

    @RequestMapping({"/archive/list", "/archive"})
    public String listArchives(Model model) {
        model.addAttribute("archives", archiveService.listAll());
        return "archive/list";
    }

    @RequestMapping("/Archive/show/{id}")
    public String getArchive(@PathVariable String id, Model model) {
        Archive archive = archiveService.getById(Long.valueOf(id));
        model.addAttribute("Archive", archive);
        model.addAttribute("logs", archive.getArchivedChangeLogs());
        return "archive/show";
    }
}
