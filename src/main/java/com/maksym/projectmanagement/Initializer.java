package com.maksym.projectmanagement;

import com.maksym.projectmanagement.controller.SkillController;
import com.maksym.projectmanagement.controller.UserController;
import com.maksym.projectmanagement.repository.*;
import com.maksym.projectmanagement.view.MainView;
import com.maksym.projectmanagement.view.UserView;

public class Initializer {
    private CustomerRepository customerRepository;
    private ProjectRepository projectRepository;
    private TeamRepository teamRepository;
    private UserRepository userRepository;
    private SkillRepository skillRepository;

    private UserController userController;
    private SkillController skillController;

    private MainView mainView;
    private UserView userView;

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

        mainView = new MainView();
        userView = new UserView();


        userRepository.setSkillRepository(skillRepository);
        teamRepository.setUserRepository(userRepository);
        customerRepository.setProjectRepository(projectRepository);
        projectRepository.setCustomerRepository(customerRepository);
        projectRepository.setTeamRepository(teamRepository);

        userController.setUserRepository(userRepository);
        userController.setSkillController(skillController);
        skillController.setSkillRepository(skillRepository);

        mainView.setUserView(userView);
        userView.setMainView(mainView);
        userView.setUserController(userController);
        userView.setSkillController(skillController);
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
