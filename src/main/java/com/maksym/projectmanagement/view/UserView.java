package com.maksym.projectmanagement.view;

import com.maksym.projectmanagement.controller.SkillController;
import com.maksym.projectmanagement.controller.UserController;
import com.maksym.projectmanagement.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.maksym.projectmanagement.Util.*;

public class UserView {
    private MainView mainView;
    private UserController userController;
    private SkillController skillController;
    private Map<String, List<String>> actions;

    public UserView() {
        this.actions = new HashMap<>();
        initActions();
    }

    public void usersMenu() {
        Integer elementId = -1;
        while (elementId != 5) {
            writeToConsole(actions.get("usersMenu"));
            elementId = readNumberFromConsole("Please enter the number of the needed section");
            switch (elementId) {
                case 1:
                    addNewUser();
                    break;
                case 2:
                    showAllUsers();
                    break;
                case 3:
                    updateUser();
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

    private void updateUser() {
        Integer userId = readNumberFromConsole("Enter the user ID:");
        User user = userController.getUser(userId);
        writeToConsole(user);

        writeToConsole("1. Change name");
        writeToConsole("2. Add skill");
        writeToConsole("3. Delete skill");

        Integer elementId = readNumberFromConsole("Please enter the number of the needed section");
        switch (elementId) {
            case 1:
                user.setName(readFromConsole("Enter new name"));
                break;
            case 2:
                writeToConsole(skillController.getAllSkills());
                Integer skillId = readNumberFromConsole("To add a new skill, enter it id");
                ////for ()//////////////////////////////////////////////
                break;
            case 3:
                updateUser();
                break;
        }

        userController.getUser(userId);
    }

    private void deleteUser() {
        Integer userId = readNumberFromConsole("Enter the user ID to delete:");
        writeToConsole(userController.delete(userId) ? "SUCCESS" : "FAILED");
    }

    public void showAllUsers() {
        List<User> users = userController.getAll();
        if (users == null) {
            writeToConsole("NO SAVED USERS");
        } else {
            writeToConsole(users);
        }
    }

    public void showUser(Integer userId) {
        User user = userController.getUser(userId);
        if (user == null) {
            writeToConsole("NO USER WITH ID: " + userId);
        } else {
            writeToConsole(user);
        }
    }


    private void initActions() {
        ArrayList<String> usersMenu = new ArrayList<String>() {{
            add("1. Add new user");
            add("2. Show all users");
            add("3. Update user");
            add("4. Delete user");
            add("5. Back to main menu");
        }};
        actions.put("usersMenu", usersMenu);
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
