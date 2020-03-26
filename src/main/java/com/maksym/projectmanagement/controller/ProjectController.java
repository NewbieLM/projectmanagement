package com.maksym.projectmanagement.controller;

import com.maksym.projectmanagement.model.Project;
import com.maksym.projectmanagement.repository.ProjectRepository;

import java.util.List;

public class ProjectController {
    private ProjectRepository projectRepository;

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public Project get(Integer projectId) {
        return projectRepository.get(projectId);
    }

    public List<Project> getAll() {
        return projectRepository.getAll();
    }

    public void update(Project project) {
        projectRepository.update(project);
    }

    public boolean delete(Integer projectId) {
        return projectRepository.delete(projectId);
    }

    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
}
