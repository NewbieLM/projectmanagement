package com.maksym.projectmanagement.repository;

import com.maksym.projectmanagement.Util;
import com.maksym.projectmanagement.model.Skill;
import com.maksym.projectmanagement.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserRepository {
    private static final String SAVE_USER = "INSERT INTO company.users (name) VALUE (?)";
    private static final String GET_USERS_BY_TEAM_ID = "SELECT userid FROM company.teamusers WHERE teamid = ?";
    private static final String GET_USERS_BY_ID = "SELECT * FROM company.users WHERE id IN ";
    private static final String GET_ALL_USERS = "SELECT * FROM company.users";
    private static final String DELETE_USER = "DELETE FROM company.users WHERE id = ?";
    private static final String UPDATE_USER = "UPDATE company.users SET name = ? WHERE id = ?";

    private SkillRepository skillRepository;

    public User save(User user) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(SAVE_USER, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, user.getName());

        int updated = statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        int userId = 0;
        if (updated > 0) {
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            userId = resultSet.getInt(1);
        }

        Util.closeConnection(connection, statement, resultSet);

        if (userId != 0) {
            user.setId(userId);
            skillRepository.updateUserSkills(user.getSkills(), userId);
            return user;
        }

        return null;
    }

    public List<User> get(Integer... usersId) throws SQLException {
        List<User> users = new ArrayList<>();
        String parameters = Util.arrayToQueryParameters(usersId);
        Map<Integer, List<Skill>> skills = skillRepository.getUserSkills(usersId);
        Connection connection = Util.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_USERS_BY_ID + parameters);


        while (resultSet.next()) {
            Integer id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            List<Skill> userSkills = skills.getOrDefault(id, new ArrayList<>());
            users.add(new User(id, name, userSkills));
        }

        Util.closeConnection(connection, statement, resultSet);

        return users;
    }

    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();

        Connection connection = Util.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_ALL_USERS);

        while (resultSet.next()) {
            Integer id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            users.add(new User(id, name, null));
        }

        Util.closeConnection(connection, statement, resultSet);

        Map<Integer, List<Skill>> skills = skillRepository.getUserSkills(users.stream().map(u -> u.getId()).toArray(Integer[]::new));
        for (User user : users) {
            List<Skill> userSkills = skills.getOrDefault(user.getId(), new ArrayList<>());
            user.setSkills(userSkills);
        }


        return users;
    }

    public List<User> getByTeam(Integer teamId) throws SQLException {
        Integer[] teammates;
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_USERS_BY_TEAM_ID);
        statement.setInt(1, teamId);
        ResultSet resultSet = statement.executeQuery();
        resultSet.last();
        teammates = new Integer[resultSet.getRow()];
        resultSet.beforeFirst();
        for (int i = 0; resultSet.next(); i++) {
            teammates[i] = resultSet.getInt(1);
        }

        Util.closeConnection(connection, statement, resultSet);

        return teammates.length == 0 ? new ArrayList<>() : get(teammates);
    }

    public boolean update(User user) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_USER);
        statement.setString(1, user.getName());
        statement.setInt(2, user.getId());

        int updated = statement.executeUpdate();

        Util.closeConnection(connection, statement);

        boolean skillsUpdated = skillRepository.updateUserSkills(user.getSkills(), user.getId());

        return updated > 0 || skillsUpdated;
    }

    public boolean delete(Integer userId) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_USER);
        statement.setInt(1, userId);
        int updated = statement.executeUpdate();

        Util.closeConnection(connection, statement);

        return updated > 0;
    }

    public void setSkillRepository(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }
}
