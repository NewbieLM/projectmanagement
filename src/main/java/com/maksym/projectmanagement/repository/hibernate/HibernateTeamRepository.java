package com.maksym.projectmanagement.repository.hibernate;

import com.maksym.projectmanagement.model.Team;

import java.util.List;

public class HibernateTeamRepository {

    public Team save(Team team) {
        return null;
    }

    public Team get(Integer teamId) {
        return null;
    }

    public List<Team> getAll() {
        return null;
    }

    public List<Team> getByProject(Integer projectId) {
        return null;
    }

    public boolean update(Team team) {
        return false;
    }

    public boolean delete(Integer teamId) {
        return false;
    }

    public boolean addUser(Integer teamId, Integer... usersId) {
        return false;
    }

    public boolean deleteUser(Integer teamId, Integer... usersId) {
        return false;
    }
}
