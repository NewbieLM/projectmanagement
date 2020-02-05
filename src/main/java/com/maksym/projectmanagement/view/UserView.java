package com.maksym.projectmanagement.view;

import com.maksym.projectmanagement.controller.SkillController;
import com.maksym.projectmanagement.controller.UserController;
import com.maksym.projectmanagement.model.Skill;
import com.maksym.projectmanagement.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.maksym.projectmanagement.Util.*;

public class UserView {
    private MainView mainView;
    private UserController userController;
    private SkillController skillController;
    private List<String> actions;

    public UserView() {
        this.actions = initActions();
    }

    public void usersMenu() {
        Integer elementId = -1;
        while (elementId != 5) {
            writeToConsole(actions);
            elementId = readNumberFromConsole("Please enter the number of the needed section");
            switch (elementId) {
                case 1:
                    addNewUser();
                    break;
                case 2:
                    showAllUsers();
                    break;
                case 3:
                    Integer userId = readNumberFromConsole("Enter the user ID:");
                    updateUser(userId);
                    break;
                case 4:
                    deleteUser();
                    break;
                case 5:
                    break;
            }

        }
    }

    private void addNewUser() {
        String name = readFromConsole("Enter user name:").trim();
        writeToConsole("Select skills from list:");
        writeToConsole(skillController.getAllSkills());
        String skills = readFromConsole("Rewrite skills you need (without ID), dividing them by spaces");
        User user = userController.saveUser(name, skills);
        if (user == null || user.getId() == 0) {
            writeToConsole("FAILED, TRY AGAIN");
        } else {
            writeToConsole("SUCCESS");
            writeToConsole(user);
        }
    }

    private void updateUser(Integer userId) {
        Integer elementId = -1;
        while (elementId != 4) {
            User user = userController.getUser(userId);
            if (user == null) {
                writeToConsole("NO USER WITH ID: " + userId);
                return;
            } else {
                writeToConsole(user);
            }

            writeToConsole("1. Change name");
            writeToConsole("2. Add skill");
            writeToConsole("3. Delete skill");
            writeToConsole("4. Back");

            elementId = readNumberFromConsole("Please enter the number of the needed section");
            Integer skillId = null;

            switch (elementId) {
                case 1:
                    user.setName(readFromConsole("Enter new name"));
                    break;
                case 2:
                    List<Skill> skills = skillController.getAllSkills();
                    writeToConsole(skills.stream().filter(s -> !user.getSkills().contains(s)).collect(Collectors.toList()));
                    skillId = readNumberFromConsole("To add a new skill, enter skill ID");

                    for (Skill skill : skills) {
                        if (skill.getId() == skillId) {
                            user.addSkill(skill);
                        }
                    }
                    break;
                case 3:
                    skillId = readNumberFromConsole("To delete skill, enter skill ID");
                    if (skillId != null) {
                        user.deleteSkill(skillId);
                    }
                    break;
                case 4:
                    return;
            }

            boolean updated = userController.update(user);
            writeToConsole(updated ? "SUCCESS" : "FAILED");
        }
    }

    private void deleteUser() {
        Integer userId = readNumberFromConsole("Enter the user ID to delete:");
        writeToConsole(userController.delete(userId) ? "SUCCESS" : "FAILED");
    }

    public void showAllUsers() {
        List<User> users = userController.getAll();
        if (users == null || users.isEmpty()) {
            writeToConsole("NO SAVED USERS");
        } else {
            writeToConsole(users);
        }
    }

    private List<String> initActions() {
        ArrayList<String> usersMenu = new ArrayList<String>() {{
            add("1. Add new user");
            add("2. Show all users");
            add("3. Update user");
            add("4. Delete user");
            add("5. Back to main menu");
        }};
        return usersMenu;
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public void setSkillController(SkillController skillController) {
        this.skillController = skillController;
    }
}
