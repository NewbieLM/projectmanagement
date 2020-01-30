package com.maksym.projectmanagement.repository;

import com.maksym.projectmanagement.Util;
import com.maksym.projectmanagement.model.Team;
import com.maksym.projectmanagement.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamRepository {
    private static final String GET_ALL_TEAMS = "SELECT * FROM company.teams";
    private static final String GET_TEAM_BY_ID = "SELECT * FROM company.teams WHERE id = ?";
    private static final String DELETE_TEAM = "DELETE FROM company.teams WHERE id = ?";
    private static final String UPDATE_TEAM = "UPDATE company.teams SET description = ? WHERE id = ?";
    private static final String ADD_USER_TO_TEAM = "INSERT INTO company.teamusers (teamid, userid) VALUE (?, ?)";
    private static final String DELETE_USER_FROM_TEAM = "DELETE FROM company.teamusers WHERE teamid = ? AND userid IN";

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


    public boolean update(Team team) throws SQLException {
        List<User> storedUsers = userRepository.getByTeam(team.getId());

        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_TEAM);
        statement.setString(1, team.getDescription());
        statement.setInt(2, team.getId());
        int updated = statement.executeUpdate();

        Util.closeConnection(connection, statement);

        List<Integer> newUsers = new ArrayList<>();
        for (User user : team.getUsers()) {
            if (!storedUsers.contains(user)) {
                newUsers.add(user.getId());
            }
        }
        boolean newUsersSaved = true;
        if (newUsers.size() > 0) {
            newUsersSaved = addUser(team.getId(), newUsers);
        }

        List<Integer> removalUsers = new ArrayList<>();
        for (User user : storedUsers) {
            if (!team.getUsers().contains(user)) {
                removalUsers.add(user.getId());
            }
        }
        boolean usersDeleted = true;
        if (removalUsers.size() > 0) {
            usersDeleted = deleteUser(team.getId(), removalUsers);
        }


        return updated > 0 && newUsersSaved && usersDeleted;
    }

    public boolean addUser(Integer teamId, List<Integer> usersId) throws SQLException {
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
        Util.closeConnection(connection, statement);

        return newUsers.length > 0;
    }

    public boolean deleteUser(Integer teamId, List<Integer> usersId) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_USER_FROM_TEAM + Util.arrayToQueryParameters(usersId.toArray()));
        statement.setInt(1, teamId);
        int deleted = statement.executeUpdate();
        Util.closeConnection(connection, statement);

        return deleted > 0;
    }

    public boolean delete(Integer teamId) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_TEAM);
        statement.setInt(1, teamId);
        int updated = statement.executeUpdate();

        Util.closeConnection(connection, statement);

        return updated > 0;
    }


}
