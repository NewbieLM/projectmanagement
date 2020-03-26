package com.maksym.projectmanagement.controller;

import com.maksym.projectmanagement.model.Skill;
import com.maksym.projectmanagement.repository.SkillRepository;

import java.util.List;

public class SkillController {
    private SkillRepository skillRepository;

    public List<Skill> getAllSkills() {
        return skillRepository.getAll();
    }

    public Skill addNewSkill(String description) {
        Skill skill = new Skill(description);
        skill = skillRepository.save(skill);
        return skill;
    }

    public Skill getSkill(Integer skillId) {
        Skill skill = skillRepository.get(skillId);
        return skill;
    }

    public void updateSkill(Skill skill) {
        skillRepository.update(skill);
    }

    public void setSkillRepository(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }
}
