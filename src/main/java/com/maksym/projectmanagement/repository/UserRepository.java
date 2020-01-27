package com.maksym.projectmanagement.repository;

import com.maksym.projectmanagement.Util;
import com.maksym.projectmanagement.model.Skill;
import com.maksym.projectmanagement.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserRepository {
    private static final String GET_USER_BY_ID = "SELECT * FROM company.users WHERE id = ?";
    private static final String SAVE_USER = "INSERT INTO company.users (name) VALUE (?)";
    private static final String DELETE_USER = "DELETE FROM company.users WHERE id = ?";

    private SkillRepository skillRepository;

    public UserRepository(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    private UserRepository() {
    }

    public User getUser(Integer userId) throws SQLException {
        User user = null;
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Integer id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            List<Skill> skills = skillRepository.getUserSkills(userId);
            user = new User(id, name, skills);
        }

        Util.closeConnection(connection, statement, resultSet);
        return user;
    }

    public boolean save(User user) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(SAVE_USER);
        statement.setString(1, user.getName());

        skillRepository.saveUserSkills(user.getSkills(), user.getId());
        int updated = statement.executeUpdate();

        Util.closeConnection(connection, statement, null);
        return updated > 0;
    }

    public boolean delete(Integer userId) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_USER);
        statement.setInt(1, userId);
        int updated = statement.executeUpdate();

        Util.closeConnection(connection, statement, null);
        return updated > 0;
    }

}
