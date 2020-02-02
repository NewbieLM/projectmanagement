package com.maksym.projectmanagement.controller;

import com.maksym.projectmanagement.model.Skill;
import com.maksym.projectmanagement.repository.SkillRepository;

import java.sql.SQLException;
import java.util.List;

public class SkillController {
    private SkillRepository skillRepository;


    public void setSkillRepository(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> getAllSkills() {
        List<Skill> result = null;
        try {
            result = skillRepository.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
