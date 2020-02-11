package com.maksym.projectmanagement.view;

import com.maksym.projectmanagement.controller.TeamController;
import com.maksym.projectmanagement.controller.UserController;
import com.maksym.projectmanagement.model.Team;
import com.maksym.projectmanagement.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.maksym.projectmanagement.util.Util.*;

public class TeamView {
    private UserController userController;
    private TeamController teamController;
    private List<String> actions;

    public TeamView() {
        this.actions = initActions();
    }

    public void teamMenu() {
        Integer elementId = -1;
        while (elementId != 5) {
            writeToConsole(actions);
            elementId = readNumberFromConsole("Please enter the number of the needed section");
            switch (elementId) {
                case 1:
                    addNewTeam();
                    break;
                case 2:
                    showAllTeams();
                    break;
                case 3:
                    Integer teamId = readNumberFromConsole("Enter the team ID:");
                    updateTeam(teamId);
                    break;
                case 4:
                    deleteTeam();
                    break;
                case 5:
                    break;
            }

        }
    }

    private void addNewTeam() {
        String description = readFromConsole("Enter team description:").trim();
        Team team = new Team(description);
        Team savedTeam = teamController.save(team);
        if (savedTeam == null) {
            writeToConsole("FAILED, TRY AGAIN");
        } else {
            writeToConsole("SUCCESS");
            updateTeam(savedTeam.getId());
        }
    }

    private void showAllTeams() {
        List<Team> teams = teamController.getAll();
        if (teams == null || teams.isEmpty()) {
            writeToConsole("NO SAVED TEAMS");
        } else {
            writeToConsole(teams);
        }
    }

    private void updateTeam(Integer teamId) {
        Integer elementId = -1;
        while (elementId != 4) {
            Team team = teamController.getTeam(teamId);
            if (team == null) {
                writeToConsole("NO TEAM WITH ID: " + teamId);
                return;
            } else {
                writeToConsole(team);
            }

            writeToConsole("1. Change team description");
            writeToConsole("2. Add user");
            writeToConsole("3. Delete user");
            writeToConsole("4. Back");
            elementId = readNumberFromConsole("Please enter the number of the needed section");
            boolean updated = false;
            switch (elementId) {
                case 1:
                    String newDescription = readFromConsole("Enter new team description");
                    team.setDescription(newDescription);
                    updated = teamController.update(team);
                    break;
                case 2:
                    List<User> users = userController.getAll();
                    writeToConsole(users.stream().filter(u -> !team.getUsers().contains(u)).collect(Collectors.toList()));
                    Integer newUserId = readNumberFromConsole("To add a new user to team, enter user ID");
                    updated = teamController.addUserToTeam(teamId, newUserId);
                    break;
                case 3:
                    writeToConsole(team.getUsers());
                    Integer userId = readNumberFromConsole("To delete user from team, enter user ID");
                    updated = teamController.deleteUserFromTeam(teamId, userId);
                    break;
                case 4:
                    return;
            }
            writeToConsole(updated ? "SUCCESS" : "FAILED");
        }
    }

    private void deleteTeam() {
        Integer teamId = readNumberFromConsole("Enter the team ID to delete:");
        writeToConsole(teamController.delete(teamId) ? "SUCCESS" : "FAILED");
    }


    public void setTeamController(TeamController teamController) {
        this.teamController = teamController;
    }

    private List<String> initActions() {
        ArrayList<String> menu = new ArrayList<String>() {{
            add("1. Add new team");
            add("2. Show all teams");
            add("3. Update team");
            add("4. Delete team");
            add("5. Back to main menu");
        }};
        return menu;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }
}
