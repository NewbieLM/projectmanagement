package com.maksym.projectmanagement.repository;

import com.maksym.projectmanagement.Util;
import com.maksym.projectmanagement.model.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamRepository {
    private static final String GET_ALL_TEAMS = "SELECT * FROM company.teams";
    private static final String GET_TEAM_BY_ID = "SELECT * FROM company.teams WHERE id = ?";

    private UserRepository userRepository;

    public TeamRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Team> getAll() throws SQLException {
        Connection connection = Util.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_ALL_TEAMS);
        List<Team> teams = new ArrayList<>();
        while (resultSet.next()) {
            Integer id = resultSet.getInt(1);
            String description = resultSet.getString(2);
            teams.add(new Team(id, description));
        }

        Util.closeConnection(connection, statement, resultSet);

        return teams;
    }

    public Team get(Integer teamId) throws SQLException {
        Team team = null;
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_TEAM_BY_ID);
        statement.setInt(1, teamId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Integer id = resultSet.getInt(1);
            String description = resultSet.getString(2);
            team = new Team(id, description);
        }
        Util.closeConnection(connection, statement, resultSet);

        if (team != null) {
            team.setUsers(userRepository.getByTeam(teamId));
        }

        return team;
    }

}
