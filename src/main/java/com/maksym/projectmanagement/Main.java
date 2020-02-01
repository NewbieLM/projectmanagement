package com.maksym.projectmanagement;

import com.maksym.projectmanagement.repository.*;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        SkillRepository skillRepository = new SkillRepository();
        UserRepository userRepository = new UserRepository(skillRepository);
        TeamRepository teamRepository = new TeamRepository(userRepository);
        CustomerRepository customerRepository = new CustomerRepository();
        ProjectRepository projectRepository = new ProjectRepository(teamRepository, customerRepository);
        customerRepository.setProjectRepository(projectRepository);

    }
}
