package com.maksym.projectmanagement.controller;

import com.maksym.projectmanagement.model.Skill;
import com.maksym.projectmanagement.repository.HibernateSkillRepository;

import java.util.List;

public class SkillController {
    private HibernateSkillRepository skillRepository;


    public void setSkillRepository(HibernateSkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> getAllSkills() {
        return skillRepository.getAll();
    }

    public Skill addNewSkill(String description) {
        Skill skill = new Skill(description);
        skill = skillRepository.saveNewSkill(skill);
        return skill;
    }

    public Skill getSkill(Integer skillId) {
        Skill skill = skillRepository.get(skillId);
        return skill;
    }

    public void updateSkill(Skill skill) {
        skillRepository.updateSkill(skill);
    }
}
