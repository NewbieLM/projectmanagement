package com.maksym.projectmanagement.model;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private Integer id;
    private String description;
    private Integer cost;
    private List<Team> teams;

    public Project(Integer id, String description, Integer cost, List<Team> teams) {
        this.id = id;
        this.description = description;
        this.cost = cost;
        this.teams = teams;
    }

    public Project(Integer id, String description, Integer cost) {
        this(id, description, cost, new ArrayList<>());
    }

    public Project(String description) {
        this(0, description, 0, new ArrayList<>());
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

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    @Override
    public String toString() {
        String str = "id=" + id + ", description='" + description + '\'' + ", cost=" + cost + "\n";
        if (teams.size() > 0) {
            str += teamsToString();
        } else {
            str += "========================= \n";
        }
        return str;
    }

    private String teamsToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Teams:");
        stringBuilder.append("\n");
        for (Team team : teams) {
            stringBuilder.append("-");
            stringBuilder.append(team);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        return id != null ? id.equals(project.id) : project.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
