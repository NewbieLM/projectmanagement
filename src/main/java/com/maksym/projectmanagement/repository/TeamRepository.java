package com.maksym.projectmanagement.repository;

import com.maksym.projectmanagement.Util;
import com.maksym.projectmanagement.model.Team;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        return teams;
    }



}
