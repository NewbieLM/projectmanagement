package com.maksym.projectmanagement.model;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project extends AbstractNamedEntity {

    @Column(name = "cost")
    private Integer cost;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "project_teams",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    private List<Team> teams;


    public Project() {
    }

    public Project(Integer id, String name, Integer cost, List<Team> teams) {
        super(id, name);
        this.cost = cost;
        this.teams = teams;
    }

    public Project(Integer id, String name, Integer cost) {
        this(id, name, cost, new ArrayList<>());
    }

    public Project(String name) {
        this(null, name, 0, new ArrayList<>());
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

    public boolean addTeam(Team team) {
        return teams.add(team);
    }

    public boolean removeTeam(Team team) {
        return teams.remove(team);
    }

    @Override
    public String toString() {
        String str = "id=" + super.getId() + ", description='" + super.getName() + '\'' + ", cost=" + cost + "\n";
        if (Hibernate.isInitialized(teams) && teams.size() > 0) {
            str += teamsToString();
        } else {
            str += "=========================\n";
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

}
