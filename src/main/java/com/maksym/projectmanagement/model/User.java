package com.maksym.projectmanagement.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Integer id;
    private String name;
    private List<Skill> skills;

    public User(Integer id, String name, List<Skill> skills) {
        this.id = id;
        this.name = name;
        this.skills = skills;
    }

    public User(String name) {
        this(0, name, new ArrayList<>());
    }

    public void addSkill(Skill skill) {
        this.skills.add(skill);
    }

    public void deleteSkill(Integer skillId) {
        for(Skill skill : skills){
            if(skill.getId() == skillId){
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", name='" + name + '\'' + "\n" +
                "skills:" + skills + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id != null ? id.equals(user.id) : user.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
