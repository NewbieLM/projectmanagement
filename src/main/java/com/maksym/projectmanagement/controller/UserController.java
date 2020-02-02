package com.maksym.projectmanagement.controller;

import com.maksym.projectmanagement.model.Skill;
import com.maksym.projectmanagement.model.User;
import com.maksym.projectmanagement.repository.SkillRepository;
import com.maksym.projectmanagement.repository.UserRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    private UserRepository userRepository;
    private SkillController skillController;

    public User saveUser (String name, String userSkills){
        List<Skill> savedSkills = skillController.getAllSkills();
        List<Skill> skills = new ArrayList<>();
        for (String userSkill : userSkills.trim().split("\\s+")){
            for (Skill skill : savedSkills){
                if(skill.getDescription().equalsIgnoreCase(userSkill)){
                    skills.add(skill);
                    break;
                }
            }
        }
        User user = new User(0, name, skills);
        try {
            user = userRepository.save(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public List<User> getAll() {
        List<User> users = null;
        try {
            users = userRepository.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public User getUser(Integer userId) {
        User user = null;
        try {
            user = userRepository.get(userId).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean delete(Integer userId) {
        boolean result = false;
        try {
            result = userRepository.delete(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setSkillController(SkillController skillController) {
        this.skillController = skillController;
    }
}
