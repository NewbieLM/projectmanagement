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

    public Skill addNewSkill(String description) {
        Skill skill = new Skill(description);
        try {
            skill = skillRepository.saveNewSkill(skill);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skill;
    }

    public Skill getSkill(Integer skillId) {
        Skill skill = null;
        try {
            skill = skillRepository.get(skillId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skill;
    }

    public boolean updateSkill(Skill skill) {
        boolean updated = false;
        try {
            updated = skillRepository.updateSkill(skill);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updated;
    }
}
