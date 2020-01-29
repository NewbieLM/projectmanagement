package com.maksym.projectmanagement;

import com.maksym.projectmanagement.model.Skill;
import com.maksym.projectmanagement.model.User;
import com.maksym.projectmanagement.repository.SkillRepository;
import com.maksym.projectmanagement.repository.TeamRepository;
import com.maksym.projectmanagement.repository.UserRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        SkillRepository skillRepository = new SkillRepository();
        UserRepository userRepository = new UserRepository(skillRepository);
        TeamRepository teamRepository = new TeamRepository(userRepository);
        Integer[] ids = {5, 6, 7, 1000 };

       System.out.println(teamRepository.get(5));
       // System.out.println(userRepository.get(5));



/*
       List<Skill> skills = new ArrayList<>();
       skills.add(new Skill(2, "someONE"));
       skills.add(new Skill(3, "someONE"));
       skills.add(new Skill(4, "someONE"));
       skills.add(new Skill(5, "someONE"));
       skills.add(new Skill(6, "someONE"));
       User user = new User(11,"EPT1222", skills);
                System.out.println(userRepository.update(user));

*/

    }
}
