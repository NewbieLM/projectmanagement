package com.maksym.projectmanagement.model;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Integer id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "cost")
    private Integer cost;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "project_teams",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    @Fetch(FetchMode.SUBSELECT)
    private List<Team> teams;


    public Project() {
    }

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
        this(null, description, 0, new ArrayList<>());
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

    public boolean addTeam(Team team) {
        return teams.add(team);
    }

    public boolean removeTeam(Team team) {
        return teams.remove(team);
    }

    @Override
    public String toString() {
        String str = "id=" + id + ", description='" + description + '\'' + ", cost=" + cost + "\n";
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
