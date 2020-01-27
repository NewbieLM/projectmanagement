package com.maksym.projectmanagement;

import com.maksym.projectmanagement.repository.SkillRepository;
import com.maksym.projectmanagement.repository.UserRepository;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        SkillRepository skillRepository = new SkillRepository();
        UserRepository userRepository = new UserRepository(skillRepository);

        userRepository.delete(13);

     /*  List<Skill> skills = new ArrayList<>();
       skills.add(new Skill(2, "someONE"));
       skills.add(new Skill(3, "someONE"));
       skills.add(new Skill(4, "someONE"));
       skills.add(new Skill(5, "someONE"));
       skills.add(new Skill(6, "someONE"));

       skillRepository.saveUserSkills(skills,14);
       */
    }
}
