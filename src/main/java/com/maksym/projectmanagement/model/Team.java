package com.maksym.projectmanagement.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private Integer id;
    private String description;
    private List<User> users;

    public Team(Integer id, String description, List<User> users) {
        this.id = id;
        this.description = description;
        this.users = users;
    }

    public Team(Integer id, String description) {
        this(id, description, new ArrayList<>());
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", description='" + description;
    }
}
