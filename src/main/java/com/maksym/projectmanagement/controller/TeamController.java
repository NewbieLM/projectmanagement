package com.maksym.projectmanagement.controller;

import com.maksym.projectmanagement.model.Team;
import com.maksym.projectmanagement.repository.TeamRepository;

import java.sql.SQLException;
import java.util.List;

public class TeamController {
    private TeamRepository teamRepository;

    public List<Team> getAll() {
        List<Team> teams = null;
        try {
            teams = teamRepository.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teams;
    }

    public Team getTeam(Integer teamId) {
        Team team = null;
        try {
            team = teamRepository.get(teamId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return team;
    }

    public Team save(Team team) {
        try {
            team = teamRepository.save(team);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return team;
    }

    public boolean updateTeam(Team team) {
        boolean updated = false;
        try {
            updated = teamRepository.update(team);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updated;
    }

    public boolean addUserToTeam(Integer teamId, Integer... userId) {
        boolean saved = false;
        try {
            saved = teamRepository.addUser(teamId, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saved;
    }

    public boolean deleteUserFromTeam(Integer teamId, Integer... userId) {
        boolean deleted = false;
        try {
            deleted = teamRepository.deleteUser(teamId, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    public boolean deleteTeam(Integer teamId) {
        boolean deleted = false;
        try {
            deleted = teamRepository.delete(teamId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleted;
    }

    public void setTeamRepository(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }
}
