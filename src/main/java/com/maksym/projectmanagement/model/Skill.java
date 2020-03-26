package com.maksym.projectmanagement.model;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "skills")
public class Skill extends AbstractNamedEntity {

    public Skill() {
    }

    public Skill(Integer id, String name) {
        super(id, name);
    }

    public Skill(String name) {
        this(null, name);
    }

    @Override
    public String toString() {
        return "[id " + super.getId() + "]" + super.getName();
    }

}
