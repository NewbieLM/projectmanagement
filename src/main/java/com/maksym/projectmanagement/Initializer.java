package com.maksym.projectmanagement;

import com.maksym.projectmanagement.controller.SkillController;
import com.maksym.projectmanagement.controller.TeamController;
import com.maksym.projectmanagement.controller.UserController;
import com.maksym.projectmanagement.repository.*;
import com.maksym.projectmanagement.view.MainView;
import com.maksym.projectmanagement.view.SkillView;
import com.maksym.projectmanagement.view.TeamView;
import com.maksym.projectmanagement.view.UserView;

public class Initializer {
    private CustomerRepository customerRepository;
    private ProjectRepository projectRepository;
    private TeamRepository teamRepository;
    private UserRepository userRepository;
    private SkillRepository skillRepository;

    private UserController userController;
    private SkillController skillController;
    private TeamController teamController;

    private MainView mainView;
    private UserView userView;
    private SkillView skillView;
    private TeamView teamView;

    public Initializer() {
        try {
            Class.forName(Util.JDBC_DRIVER);
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


        mainView = new MainView();
        userView = new UserView();
        skillView = new SkillView();
        teamView = new TeamView();


        userRepository.setSkillRepository(skillRepository);
        teamRepository.setUserRepository(userRepository);
        customerRepository.setProjectRepository(projectRepository);
        projectRepository.setCustomerRepository(customerRepository);
        projectRepository.setTeamRepository(teamRepository);

        userController.setUserRepository(userRepository);
        userController.setSkillController(skillController);
        skillController.setSkillRepository(skillRepository);
        teamController.setTeamRepository(teamRepository);

        mainView.setUserView(userView);
        mainView.setSkillView(skillView);
        mainView.setTeamView(teamView);
        userView.setMainView(mainView);
        userView.setUserController(userController);
        userView.setSkillController(skillController);
        skillView.setSkillController(skillController);
        teamView.setTeamController(teamController);
        teamView.setUserController(userController);

    }


    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public ProjectRepository getProjectRepository() {
        return projectRepository;
    }

    public TeamRepository getTeamRepository() {
        return teamRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public SkillRepository getSkillRepository() {
        return skillRepository;
    }

    public UserController getUserController() {
        return userController;
    }

    public SkillController getSkillController() {
        return skillController;
    }

    public MainView getMainView() {
        return mainView;
    }

    public UserView getUserView() {
        return userView;
    }
}
