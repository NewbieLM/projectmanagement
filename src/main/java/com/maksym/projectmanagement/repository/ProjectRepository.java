package com.maksym.projectmanagement.repository;

import com.maksym.projectmanagement.util.Util;
import com.maksym.projectmanagement.model.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectRepository {
    private static final String SAVE_PROJECT = "INSERT INTO company.projects (description, cost) VALUE (?, ?)";
    private static final String GET_ALL_PROJECT = "SELECT * FROM company.projects";
    private static final String GET_PROJECT_BY_ID = "SELECT * FROM company.projects WHERE id = ?";
    private static final String UPDATE_PROJECT = "UPDATE company.projects SET description = ?, cost = ? WHERE id = ?";
    private static final String DELETE_PROJECT = "DELETE FROM company.projects WHERE id = ?";
    private static final String ADD_TEAM_TO_PROJECT = "INSERT INTO company.project_teams (project_id, team_id) VALUE (?, ?)";
    private static final String DELETE_TEAM_FROM_PROJECT = "DELETE FROM company.project_teams WHERE project_id = ? AND team_id IN";
    private static final String GET_PROJECT_BY_CUSTOMER_ID = "SELECT p.id, p.description, p.cost, cp.customer_budget FROM company.projects p LEFT JOIN company.customer_projects cp ON cp.project_id = p.id WHERE cp.customer_id = ?";
    private static final String UPDATE_PROJECT_COST = "UPDATE company.projects p SET cost = (SELECT SUM(customer_budget) FROM company.customer_projects cp WHERE cp.project_id = p.id GROUP BY cp.project_id) WHERE p.id IN";


    private TeamRepository teamRepository;

    public Project save(Project project) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(SAVE_PROJECT, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, project.getDescription());
        statement.setInt(2, project.getCost());

        int updated = statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        int projectId = 0;
        if (updated > 0) {
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            projectId = resultSet.getInt(1);
        }

        Util.closeConnection(statement, resultSet);
        if (projectId != 0) {
            project.setId(projectId);
            return project;
        }

        return null;
    }

    public Project get(Integer projectId) throws SQLException {
        Project project = null;
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_PROJECT_BY_ID);
        statement.setInt(1, projectId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Integer id = resultSet.getInt(1);
            String description = resultSet.getString(2);
            Integer cost = resultSet.getInt(3);
            project = new Project(id, description, cost);
        }
        Util.closeConnection(statement, resultSet);

        if (project != null) {
            project.setTeams(teamRepository.getByProject(projectId));
        }

        return project;
    }

    public List<Project> getAll() throws SQLException {
        List<Project> projects = new ArrayList<>();

        Connection connection = Util.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_ALL_PROJECT);
        while (resultSet.next()) {
            Integer id = resultSet.getInt(1);
            String description = resultSet.getString(2);
            Integer cost = resultSet.getInt(3);
            projects.add(new Project(id, description, cost));
        }

        Util.closeConnection(statement, resultSet);

        return projects;
    }

    public Map<Project, Integer> getByCustomer(Integer customerId) throws SQLException {
        Map<Project, Integer> projects = new HashMap<>();
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_PROJECT_BY_CUSTOMER_ID);
        statement.setInt(1, customerId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Integer id = resultSet.getInt(1);
            String description = resultSet.getString(2);
            Integer cost = resultSet.getInt(3);
            Integer customerBudget = resultSet.getInt(4);
            projects.put(new Project(id, description, cost), customerBudget);
        }
        Util.closeConnection(statement, resultSet);

        return projects;
    }

    public boolean updateProject(Project project) throws SQLException {

        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_PROJECT);
        statement.setString(1, project.getDescription());
        statement.setInt(2, project.getCost());
        statement.setInt(3, project.getId());
        int updated = statement.executeUpdate();

        Util.closeConnection(statement);

        return updated > 0;
    }

    public boolean updateProjectTotalCost(Integer... projectsId) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_PROJECT_COST + Util.arrayToQueryParameters(projectsId));
        int updated = statement.executeUpdate();
        Util.closeConnection(statement);

        return updated > 0;

    }

    public boolean delete(Integer projectId) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_PROJECT);
        statement.setInt(1, projectId);
        int updated = statement.executeUpdate();

        Util.closeConnection(statement);

        return updated > 0;
    }

    public boolean addTeam(Integer projectId, Integer... teamsId) throws SQLException {
        Connection connection = Util.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement(ADD_TEAM_TO_PROJECT);
        for (Integer teamId : teamsId) {
            statement.setInt(1, projectId);
            statement.setInt(2, teamId);
            statement.addBatch();
        }
        int[] newTeams = statement.executeBatch();

        connection.commit();
        Util.closeConnection(statement);

        return newTeams.length > 0;
    }

    public boolean deleteTeam(Integer projectId, Integer... teamsId) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_TEAM_FROM_PROJECT + Util.arrayToQueryParameters(teamsId));
        statement.setInt(1, projectId);
        int deleted = statement.executeUpdate();
        Util.closeConnection(statement);

        return deleted > 0;
    }

    public void setTeamRepository(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

}
