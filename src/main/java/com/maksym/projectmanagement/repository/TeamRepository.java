package com.maksym.projectmanagement.repository;

import com.maksym.projectmanagement.util.Util;
import com.maksym.projectmanagement.model.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamRepository {
    private static final String SAVE_TEAM = "INSERT INTO company.teams (description) VALUE (?)";
    private static final String GET_TEAM_BY_ID = "SELECT * FROM company.teams WHERE id = ?";
    private static final String GET_ALL_TEAMS = "SELECT * FROM company.teams";
    private static final String GET_TEAM_BY_PROJECT_ID = "SELECT t.id, t.description FROM company.teams t LEFT JOIN company.project_teams pt ON pt.team_id = t.id WHERE pt.project_id = ?";
    private static final String UPDATE_TEAM = "UPDATE company.teams SET description = ? WHERE id = ?";
    private static final String DELETE_TEAM = "DELETE FROM company.teams WHERE id = ?";
    private static final String ADD_USER_TO_TEAM = "INSERT INTO company.team_users (team_id, user_id) VALUE (?, ?)";
    private static final String DELETE_USER_FROM_TEAM = "DELETE FROM company.team_users WHERE team_id = ? AND user_id IN";


    private UserRepository userRepository;

    public Team save(Team team) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(SAVE_TEAM, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, team.getDescription());

        int updated = statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        int teamId = 0;
        if (updated > 0) {
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            teamId = resultSet.getInt(1);
        }

        Util.closeConnection(statement, resultSet);
        if (teamId != 0) {
            team.setId(teamId);
            return team;
        }

        return null;
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
        Util.closeConnection(statement, resultSet);

        if (team != null) {
            team.setUsers(userRepository.getByTeam(teamId));
        }

        return team;
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

        Util.closeConnection(statement, resultSet);

        return teams;
    }

    public List<Team> getByProject(Integer projectId) throws SQLException {
        List<Team> teams = new ArrayList<>();
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_TEAM_BY_PROJECT_ID);
        statement.setInt(1, projectId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Integer id = resultSet.getInt(1);
            String description = resultSet.getString(2);
            teams.add(new Team(id, description));
        }
        Util.closeConnection(statement, resultSet);

        return teams;
    }

    public boolean update(Team team) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_TEAM);
        statement.setString(1, team.getDescription());
        statement.setInt(2, team.getId());
        int updated = statement.executeUpdate();

        Util.closeConnection(statement);

        return updated > 0;
    }

    public boolean delete(Integer teamId) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_TEAM);
        statement.setInt(1, teamId);
        int updated = statement.executeUpdate();

        Util.closeConnection(statement);

        return updated > 0;
    }

    public boolean addUser(Integer teamId, Integer... usersId) throws SQLException {
        Connection connection = Util.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement(ADD_USER_TO_TEAM);
        for (Integer userId : usersId) {
            statement.setInt(1, teamId);
            statement.setInt(2, userId);
            statement.addBatch();
        }
        int[] newUsers = statement.executeBatch();

        connection.commit();
        Util.closeConnection(statement);

        return newUsers.length > 0;
    }

    public boolean deleteUser(Integer teamId, Integer... usersId) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_USER_FROM_TEAM + Util.arrayToQueryParameters(usersId));
        statement.setInt(1, teamId);
        int deleted = statement.executeUpdate();
        Util.closeConnection(statement);

        return deleted > 0;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
