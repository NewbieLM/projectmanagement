package com.maksym.projectmanagement.repository;

import com.maksym.projectmanagement.Util;
import com.maksym.projectmanagement.model.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillRepository {
    private static final String GET_ALL_SKILLS = "SELECT * FROM company.skills";
    private static final String GET_SKILL_BY_ID = "SELECT * FROM company.skills WHERE id = ?";
    private static final String INSERT_NEW_SKILL = "INSERT INTO company.skills (skill) VALUE (?)";
    private static final String UPDATE_SKILL = "UPDATE company.skills SET skill = ?  WHERE id = ?";
    private static final String GET_SKILLS_BY_USERS_ID = "SELECT us.user_id, us.skill_id, s.skill FROM company.user_skills us LEFT JOIN company.skills s ON us.skill_id = s.id WHERE us.user_id IN";
    private static final String ADD_USER_SKILL = "INSERT INTO company.user_skills (user_id, skill_id) VALUE (?, ?)";
    private static final String DELETE_USER_SKILL = "DELETE FROM company.user_skills WHERE user_id = ? AND skill_id = ?";

    public Skill saveNewSkill(Skill skill) throws SQLException {
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
        skill.setId(generatedID);

        return skill;
    }

    public List<Skill> getAll() throws SQLException {
        List<Skill> skills = new ArrayList<>();
        Connection connection = Util.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_ALL_SKILLS);

        while (resultSet.next()) {
            Integer skillId = resultSet.getInt(1);
            String description = resultSet.getString(2);
            Skill skill = new Skill(skillId, description);
            skills.add(skill);
        }

        Util.closeConnection(connection, statement, resultSet);

        return skills;
    }

    public Skill get(Integer skillId) throws SQLException {
        Skill skill = null;
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_SKILL_BY_ID);
        statement.setInt(1, skillId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Integer id = resultSet.getInt(1);
            String description = resultSet.getString(2);
            skill = new Skill(id, description);
        }

        Util.closeConnection(connection, statement, resultSet);

        return skill;
    }

    public boolean updateSkill(Skill skill) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_SKILL);
        statement.setString(1, skill.getDescription());
        statement.setInt(2, skill.getId());
        int updated = statement.executeUpdate();

        Util.closeConnection(connection, statement);

        return updated > 0;
    }


    public Map<Integer, List<Skill>> getUserSkills(Integer... usersId) throws SQLException {
        Map<Integer, List<Skill>> skills = new HashMap<>();
        String parameters = Util.arrayToQueryParameters(usersId);
        Connection connection = Util.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_SKILLS_BY_USERS_ID + parameters);

        while (resultSet.next()) {
            Integer userId = resultSet.getInt(1);
            Integer skilId = resultSet.getInt(2);
            String description = resultSet.getString(3);
            Skill skill = new Skill(skilId, description);

            if (skills.containsKey(userId)) {
                skills.get(userId).add(skill);
            } else {
                List<Skill> sk = new ArrayList<>();
                sk.add(skill);
                skills.put(userId, sk);
            }

        }

        Util.closeConnection(connection, statement, resultSet);

        return skills;
    }

    public boolean updateUserSkills(List<Skill> skills, Integer userId) throws SQLException {
        List<Skill> storedSkills = getUserSkills(userId).getOrDefault(userId, new ArrayList<>());
        Connection connection = Util.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement(ADD_USER_SKILL);
        for (Skill skill : skills) {
            if (!storedSkills.contains(skill)) {
                statement.setInt(1, userId);
                statement.setInt(2, skill.getId());
                statement.addBatch();
            }
        }

        int[] saved = statement.executeBatch();
        connection.commit();
        Util.closeConnection(connection, statement);

        List<Skill> removalSkills = new ArrayList<>();
        for (Skill skill : storedSkills) {
            if (!skills.contains(skill)) {
                removalSkills.add(skill);
            }
        }

        boolean deleted = false;
        if (removalSkills.size() > 0) {
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

        Util.closeConnection(connection, statement);

        return deleted.length > 0;
    }

}
