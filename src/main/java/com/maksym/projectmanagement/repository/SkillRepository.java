package com.maksym.projectmanagement.repository;

import com.maksym.projectmanagement.Util;
import com.maksym.projectmanagement.model.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillRepository {
    private static final String GET_SKILLS_BY_USER_ID = "SELECT s.* FROM company.skills s LEFT JOIN userskills us ON us.skillid = s.id WHERE us.userid = ?";
    private static final String INSERT_NEW_SKILL = "INSERT INTO company.skills (skill) VALUE (?)";
    private static final String INSERT_USER_SKILL = "INSERT INTO company.userskills (userid, skillid) VALUE (?, ?)";

    public List<Skill> getUserSkills(Integer userId) throws SQLException {
        List<Skill> skills = new ArrayList<>();
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_SKILLS_BY_USER_ID);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Integer id = resultSet.getInt(1);
            String skill = resultSet.getString(2);
            skills.add(new Skill(id, skill));
        }

        Util.closeConnection(connection, statement, resultSet);

        return skills;
    }

    public boolean saveUserSkills(List<Skill> skills, Integer userId) throws SQLException {
        List<Skill> storedSkills = getUserSkills(userId);
        Connection connection = Util.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement(INSERT_USER_SKILL);
        for (Skill skill : skills) {
            if (!storedSkills.contains(skill)) {
                statement.setInt(1, userId);
                statement.setInt(2, skill.getId());
                statement.addBatch();
            }
        }

        int[] count = statement.executeBatch();
        connection.commit();

        Util.closeConnection(connection, statement, null);

        return count.length > 0;
    }

    public Integer saveNewSkill(Skill skill) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(INSERT_NEW_SKILL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, skill.getDescription());
        int updated = statement.executeUpdate();

        int generatedID = 0;
        ResultSet resultSet = null;
        if (updated > 0) {
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            generatedID = resultSet.getInt(1);
        }

        Util.closeConnection(connection, statement, resultSet);

        return generatedID;
    }

}
