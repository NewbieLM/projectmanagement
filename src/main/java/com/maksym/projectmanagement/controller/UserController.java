package com.maksym.projectmanagement.controller;

import com.maksym.projectmanagement.model.Skill;
import com.maksym.projectmanagement.model.User;
import com.maksym.projectmanagement.repository.HibernateUserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private HibernateUserRepository userRepository;
    private SkillController skillController;

    public User saveUser(String name, String userSkills) {
        List<Skill> savedSkills = skillController.getAllSkills();
        List<Skill> skills = new ArrayList<>();
        for (String userSkill : userSkills.trim().split("\\s+")) {
            for (Skill skill : savedSkills) {
                if (skill.getDescription().equalsIgnoreCase(userSkill)) {
                    skills.add(skill);
                    break;
                }
            }
        }
        User user = new User(name, skills);
        user = userRepository.save(user);
        return user;
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public User getUser(Integer userId) {
        return userRepository.get(userId);
    }

    public boolean delete(Integer userId) {
        return userRepository.delete(userId);
    }


    public void setUserRepository(HibernateUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setSkillController(SkillController skillController) {
        this.skillController = skillController;
    }
}
