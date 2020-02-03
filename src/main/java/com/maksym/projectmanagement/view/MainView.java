package com.maksym.projectmanagement.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.maksym.projectmanagement.Util.readNumberFromConsole;
import static com.maksym.projectmanagement.Util.writeToConsole;


public class MainView {
    private UserView userView;
    private SkillView skillView;
    private Map<String, List<String>> actions;

    public MainView() {
        this.actions = new HashMap<>();
        initActions();
    }

    public void rootMenu() {
        Integer elementId = -1;
        while (elementId != 5) {
            writeToConsole(actions.get("rootMenuActions"));
            elementId = readNumberFromConsole("Please enter the number of the needed section");
            switch (elementId) {
                case 1:
                    userView.usersMenu();
                    break;
                case 2:
                    skillView.skillsMenu();
                    break;
                case 5:
                    break;
            }
        }

    }

    private void initActions() {
        ArrayList<String> rootMenu = new ArrayList<String>() {{
            add("1. Users");
            add("2. Skills");
            add("5. Exit");
        }};
        actions.put("rootMenuActions", rootMenu);
    }

    public void setUserView(UserView userView) {
        this.userView = userView;
    }

    public void setSkillView(SkillView skillView) {
        this.skillView = skillView;
    }
}
