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
    private static final String DELETE_USER_SKILL = "DELETE FROM company.userskills WHERE userid = ? AND skillid = ?";

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

    public boolean updateUserSkills(List<Skill> skills, Integer userId) throws SQLException {
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

        int[] saved = statement.executeBatch();
        connection.commit();
        Util.closeConnection(connection, statement, null);

        List<Skill> removalSkills = new ArrayList<>();
        for (Skill skill : storedSkills) {
            if (!skills.contains(skill)){
                removalSkills.add(skill);
            }
        }

        boolean deleted = false;
        if(removalSkills.size() > 0) {
            deleted = deleteUserSkills(removalSkills, userId);
        }

        return saved.length > 0 || deleted;
    }

    public boolean deleteUserSkills(List<Skill> skills, Integer userId) throws SQLException {
        Connection connection = Util.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement(DELETE_USER_SKILL);
        for (Skill skill : skills) {
            statement.setInt(1, userId);
            statement.setInt(2, skill.getId());
            statement.addBatch();
        }

        int[] deleted = statement.executeBatch();
        connection.commit();

        Util.closeConnection(connection, statement, null);

        return deleted.length > 0;
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
