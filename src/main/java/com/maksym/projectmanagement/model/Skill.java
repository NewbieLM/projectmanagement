package com.maksym.projectmanagement.model;

import java.util.Objects;

public class Skill {
    private Integer id;
    private String description;

    public Skill(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Skill(String description) {
        this(0, description);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "[id " + id + "]" + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return Objects.equals(id, skill.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
