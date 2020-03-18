package com.maksym.projectmanagement.util;

import com.maksym.projectmanagement.controller.*;
import com.maksym.projectmanagement.repository.*;
import com.maksym.projectmanagement.view.*;

public class Initializer {
    private HibernateCustomerRepository hibernateCustomerRepository;
    private HibernateProjectRepository hibernateProjectRepository;
    private HibernateTeamRepository hibernateTeamRepository;
    private HibernateUserRepository hibernateUserRepository;
    private HibernateSkillRepository hibernateSkillRepository;

    private UserController userController;
    private SkillController skillController;
    private TeamController teamController;
    private ProjectController projectController;
    private CustomerController customerController;

    private MainView mainView;
    private UserView userView;
    private SkillView skillView;
    private TeamView teamView;
    private ProjectView projectView;
    private CustomerView customerView;

    public Initializer() {
        try {
            Class.forName(Util.getJdbcDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        initLayers();
        mainView.rootMenu();
    }

    private void initLayers() {
        hibernateSkillRepository = new HibernateSkillRepository();
        hibernateUserRepository = new HibernateUserRepository();
        hibernateTeamRepository = new HibernateTeamRepository();
        hibernateProjectRepository = new HibernateProjectRepository();
        hibernateCustomerRepository = new HibernateCustomerRepository();

        skillController = new SkillController();
        userController = new UserController();
        teamController = new TeamController();
        projectController = new ProjectController();
        customerController = new CustomerController();

        mainView = new MainView();
        userView = new UserView();
        skillView = new SkillView();
        teamView = new TeamView();
        projectView = new ProjectView();
        customerView = new CustomerView();

        userController.setUserRepository(hibernateUserRepository);
        userController.setSkillController(skillController);
        skillController.setSkillRepository(hibernateSkillRepository);
        teamController.setTeamRepository(hibernateTeamRepository);
        projectController.setProjectRepository(hibernateProjectRepository);
        customerController.setCustomerRepository(hibernateCustomerRepository);

        mainView.setUserView(userView);
        mainView.setSkillView(skillView);
        mainView.setTeamView(teamView);
        mainView.setProjectView(projectView);
        mainView.setCustomerView(customerView);
        userView.setMainView(mainView);
        userView.setUserController(userController);
        userView.setSkillController(skillController);
        skillView.setSkillController(skillController);
        teamView.setTeamController(teamController);
        teamView.setUserController(userController);
        projectView.setProjectController(projectController);
        projectView.setTeamController(teamController);
        customerView.setCustomerController(customerController);
        customerView.setProjectController(projectController);

    }

}
