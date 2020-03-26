package com.maksym.projectmanagement.model;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team extends AbstractNamedEntity {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "team_users",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    public Team() {
    }

    public Team(Integer id, String name, List<User> users) {
        super(id, name);
        this.users = users;
    }

    public Team(Integer id, String name) {
        this(id, name, new ArrayList<>());
    }

    public Team(String name) {
        this(null, name, new ArrayList<>());
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public boolean addUser(User user) {
        return users.add(user);
    }

    public boolean removeUser(User user) {
        return users.remove(user);
    }

    @Override
    public String toString() {
        String str = "id=" + super.getId() + ", description=" + super.getName() + "\n";
        if (Hibernate.isInitialized(users) && users.size() > 0) {
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
