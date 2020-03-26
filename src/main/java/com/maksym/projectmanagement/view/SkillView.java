package com.maksym.projectmanagement.view;

import com.maksym.projectmanagement.controller.SkillController;
import com.maksym.projectmanagement.model.Skill;

import java.util.ArrayList;
import java.util.List;

import static com.maksym.projectmanagement.util.IOUtil.*;

public class SkillView {
    private SkillController skillController;
    private List<String> actions;

    public SkillView() {
        this.actions = initActions();
    }

    public void skillsMenu() {
        Integer elementId = -1;
        while (elementId != 4) {
            writeToConsole(actions);
            elementId = readNumberFromConsole("Please enter the number of the needed section");
            switch (elementId) {
                case 1:
                    showAllSkills();
                    break;
                case 2:
                    addNewSkill();
                    break;
                case 3:
                    updateSkill();
                    break;
                case 4:
                    break;
            }

        }
    }

    private void addNewSkill() {
        String description = readFromConsole("To add a new skill, enter a description of this skill");
        Skill skill = skillController.addNewSkill(description);
        writeToConsole(skill != null && skill.getId() > 0 ? "SUCCESS" : "FAILED");
        writeToConsole(skill);
    }

    private void showAllSkills() {
        writeToConsole(skillController.getAllSkills());
    }

    private void updateSkill() {
        Integer skillId = readNumberFromConsole("Enter the skill ID:");
        Skill skill = skillController.getSkill(skillId);
        if (skill == null) {
            writeToConsole("No skill with such id:" + skillId + ". Try again.");
            return;
        }

        writeToConsole(skill);
        String newDescription = readFromConsole("Enter new description");
        skill.setName(newDescription);
        skillController.updateSkill(skill);
        writeToConsole(skill);
    }


    private List<String> initActions() {
        ArrayList<String> menu = new ArrayList<String>() {{
            add("1. Show all skills");
            add("2. Add skill");
            add("3. Update skill");
            add("4. Back to main menu");
        }};
        return menu;
    }

    public void setSkillController(SkillController skillController) {
        this.skillController = skillController;
    }
}
