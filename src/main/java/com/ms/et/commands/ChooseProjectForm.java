package com.ms.et.commands;

import com.ms.et.domain.Project;

public class ChooseProjectForm {

    private Long itemId;
    private Project project;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
