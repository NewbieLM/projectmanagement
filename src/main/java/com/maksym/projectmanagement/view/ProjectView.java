package com.maksym.projectmanagement.view;

import com.maksym.projectmanagement.controller.ProjectController;
import com.maksym.projectmanagement.controller.TeamController;
import com.maksym.projectmanagement.model.Project;
import com.maksym.projectmanagement.model.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.maksym.projectmanagement.util.Util.*;

public class ProjectView {
    private ProjectController projectController;
    private TeamController teamController;
    private List<String> actions;

    public ProjectView() {
        this.actions = initActions();
    }

    public void projectMenu() {
        Integer elementId = -1;
        while (elementId != 5) {
            writeToConsole(actions);
            elementId = readNumberFromConsole("Please enter the number of the needed section");
            switch (elementId) {
                case 1:
                    addNewProject();
                    break;
                case 2:
                    showAllProjects();
                    break;
                case 3:
                    Integer projectId = readNumberFromConsole("Enter the project ID:");
                    updateProject(projectId);
                    break;
                case 4:
                    deleteProject();
                    break;
                case 5:
                    break;
            }

        }
    }

    private void addNewProject() {
        String description = readFromConsole("Enter project description:").trim();
        Project project = new Project(description);
        project = projectController.save(project);
        if (project.getId() == null) {
            writeToConsole("FAILED, TRY AGAIN");
        } else {
            writeToConsole("SUCCESS");
            updateProject(project.getId());
        }
    }

    private void showAllProjects() {
        List<Project> projects = projectController.getAll();
        if (projects == null || projects.isEmpty()) {
            writeToConsole("NO SAVED PROJECTS");
        } else {
            writeToConsole(projects);
        }
    }


    private void updateProject(Integer projectId) {
        Integer elementId = -1;
        while (elementId != 4) {
            Project project = projectController.get(projectId);
            if (project == null) {
                writeToConsole("NO PROJECT WITH ID: " + projectId);
                return;
            } else {
                writeToConsole(project);
            }

            writeToConsole("1. Change project description");
            writeToConsole("2. Add team");
            writeToConsole("3. Delete team");
            writeToConsole("4. Back");
            elementId = readNumberFromConsole("Please enter the number of the needed section");
            switch (elementId) {
                case 1:
                    String newDescription = readFromConsole("Enter new project description");
                    project.setDescription(newDescription);
                    projectController.update(project);
                    break;
                case 2:
                    List<Team> teams = teamController.getAll();
                    writeToConsole(teams.stream().filter(t -> !project.getTeams().contains(t)).collect(Collectors.toList()));
                    Integer newTeamId = readNumberFromConsole("To add a new team to project, enter team ID");
                    Team team = teams.stream().filter(t -> t.getId().equals(newTeamId)).findFirst().orElse(null);
                    if (team != null) {
                        project.addTeam(team);
                        projectController.update(project);
                    }
                    break;
                case 3:
                    writeToConsole(project.getTeams());
                    Integer teamId = readNumberFromConsole("To delete team from project, enter team ID");
                    team = project.getTeams().stream().filter(t -> t.getId().equals(teamId)).findFirst().orElse(null);
                    if (team != null) {
                        project.removeTeam(team);
                        projectController.update(project);
                    }
                    break;
                case 4:
                    return;
            }
        }
    }


    private void deleteProject() {
        Integer projectId = readNumberFromConsole("Enter the project ID to delete:");
        writeToConsole(projectController.delete(projectId) ? "SUCCESS" : "FAILED");
    }


    private List<String> initActions() {
        return new ArrayList<String>() {{
            add("1. Add new project");
            add("2. Show all projects");
            add("3. Update project");
            add("4. Delete project");
            add("5. Back to main menu");
        }};
    }

    public void setProjectController(ProjectController projectController) {
        this.projectController = projectController;
    }

    public void setTeamController(TeamController teamController) {
        this.teamController = teamController;
    }
}
