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

    public Team(String description) {
        this(0, description, new ArrayList<>());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        String str = "id=" + id + ", description=" + description + "\n";
        if (users.size() > 0) {
            str += usersToString();
        }
        str += "=========================";

        return str;
    }

    private String usersToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Users:");
        stringBuilder.append("\n");
        for (User user : users) {
            stringBuilder.append("-");
            stringBuilder.append(user);
            stringBuilder.append("----------------------- \n");
        }
        return stringBuilder.toString();
    }
}
