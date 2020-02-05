package com.maksym.projectmanagement.controller;

import com.maksym.projectmanagement.model.Project;
import com.maksym.projectmanagement.repository.ProjectRepository;

import java.sql.SQLException;
import java.util.List;

public class ProjectController {
    private ProjectRepository projectRepository;

    public Project save(Project project) {
        try {
            project = projectRepository.save(project);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    public Project get(Integer projectId) {
        Project project = null;
        try {
            project = projectRepository.get(projectId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    public List<Project> getAll() {
        List<Project> projects = null;
        try {
            projects = projectRepository.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public boolean update(Project project) {
        boolean updated = false;
        try {
            updated = projectRepository.updateProject(project);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updated;
    }

    public boolean delete(Integer projectId) {
        boolean deleted = false;
        try {
            deleted = projectRepository.delete(projectId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    public boolean addTeam(Integer projectId, Integer teamId) {
        boolean saved = false;
        try {
            saved = projectRepository.addTeam(projectId, teamId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saved;
    }

    public boolean deleteTeam(Integer projectId, Integer teamId) {
        boolean deleted = false;
        try {
            deleted = projectRepository.deleteTeam(projectId, teamId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
}
