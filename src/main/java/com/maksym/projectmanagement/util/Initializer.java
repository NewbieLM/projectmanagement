package com.maksym.projectmanagement.util;

import com.maksym.projectmanagement.controller.*;
import com.maksym.projectmanagement.repository.*;
import com.maksym.projectmanagement.view.*;

public class Initializer {
    private CustomerRepository customerRepository;
    private ProjectRepository projectRepository;
    private TeamRepository teamRepository;
    private UserRepository userRepository;
    private SkillRepository skillRepository;

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
        skillRepository = new SkillRepository();
        userRepository = new UserRepository();
        teamRepository = new TeamRepository();
        customerRepository = new CustomerRepository();
        projectRepository = new ProjectRepository();

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

        userRepository.setSkillRepository(skillRepository);
        teamRepository.setUserRepository(userRepository);
        customerRepository.setProjectRepository(projectRepository);
        projectRepository.setTeamRepository(teamRepository);

        userController.setUserRepository(userRepository);
        userController.setSkillController(skillController);
        skillController.setSkillRepository(skillRepository);
        teamController.setTeamRepository(teamRepository);
        projectController.setProjectRepository(projectRepository);
        customerController.setCustomerRepository(customerRepository);

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
