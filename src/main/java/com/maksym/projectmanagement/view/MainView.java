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
    private TeamView teamView;
    private ProjectView projectView;
    private CustomerView customerView;
    private Map<String, List<String>> actions;

    public MainView() {
        this.actions = new HashMap<>();
        initActions();
    }

    public void rootMenu() {
        Integer elementId = -1;
        while (elementId != 6) {
            writeToConsole(actions.get("rootMenuActions"));
            elementId = readNumberFromConsole("Please enter the number of the needed section");
            switch (elementId) {
                case 1:
                    customerView.customerMenu();
                    break;
                case 2:
                    projectView.projectMenu();
                    break;
                case 3:
                    teamView.teamMenu();
                    break;
                case 4:
                    userView.usersMenu();
                    break;
                case 5:
                    skillView.skillsMenu();
                    break;
                case 6:
                    break;
            }
        }

    }

    private void initActions() {
        ArrayList<String> rootMenu = new ArrayList<String>() {{
            add("1. Customers");
            add("2. Projects");
            add("3. Teams");
            add("4. Users");
            add("5. Skills");
            add("6. Exit");
        }};
        actions.put("rootMenuActions", rootMenu);
    }

    public void setUserView(UserView userView) {
        this.userView = userView;
    }

    public void setSkillView(SkillView skillView) {
        this.skillView = skillView;
    }

    public void setTeamView(TeamView teamView) {
        this.teamView = teamView;
    }

    public void setProjectView(ProjectView projectView) {
        this.projectView = projectView;
    }

    public void setCustomerView(CustomerView customerView) {
        this.customerView = customerView;
    }
}
