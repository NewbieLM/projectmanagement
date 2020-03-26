package com.maksym.projectmanagement.model;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends AbstractNamedEntity {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_skills",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills;


    public User() {
    }

    public User(Integer id, String name, List<Skill> skills) {
        super(id, name);
        this.skills = skills;
    }

    public User(String name, List<Skill> skills) {
        this(null, name, skills);
    }

    public User(String name) {
        this(null, name, new ArrayList<>());
    }

    public void addSkill(Skill skill) {
        this.skills.add(skill);
    }

    public void deleteSkill(Integer skillId) {
        for (Skill skill : skills) {
            if (skill.getId().equals(skillId)) {
                skills.remove(skill);
                break;
            }
        }
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        String str = "id=" + super.getId() + ", name='" + super.getName() + '\'' + "\n";
        if (Hibernate.isInitialized(skills) && skills.size() > 0) {
            str += skillsToString();
        }
        return str;
    }

    private String skillsToString() {
        return "skills:" + skills + "\n";
    }

}
