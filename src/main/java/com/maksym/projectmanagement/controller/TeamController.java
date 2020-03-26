package com.maksym.projectmanagement.controller;

import com.maksym.projectmanagement.model.Team;
import com.maksym.projectmanagement.repository.TeamRepository;

import java.util.List;

public class TeamController {
    private TeamRepository teamRepository;

    public List<Team> getAll() {
        return teamRepository.getAll();
    }

    public Team getTeam(Integer teamId) {
        return teamRepository.get(teamId);
    }

    public Team save(Team team) {
        return teamRepository.save(team);
    }

    public void update(Team team) {
        teamRepository.update(team);
    }

    public boolean delete(Integer teamId) {
        return teamRepository.delete(teamId);
    }

    public void setTeamRepository(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }
}
