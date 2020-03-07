package com.maksym.projectmanagement.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "team_users",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Fetch(FetchMode.SUBSELECT)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        return id != null ? id.equals(team.id) : team.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
