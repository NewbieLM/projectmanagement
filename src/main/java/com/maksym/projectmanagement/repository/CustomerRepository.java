package com.maksym.projectmanagement.repository;

import com.maksym.projectmanagement.Util;
import com.maksym.projectmanagement.model.Customer;
import com.maksym.projectmanagement.model.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerRepository {
    private static final String SAVE_CUSTOMER = "INSERT INTO company.customers (name) VALUE (?)";
    private static final String GET_CUSTOMER_BY_ID = "SELECT * FROM company.customers WHERE id = ?";
    private static final String GET_ALL_CUSTOMERS = "SELECT * FROM company.customers";
    private static final String UPDATE_CUSTOMER = "UPDATE company.customers SET name = ? WHERE id = ?";
    private static final String DELETE_CUSTOMER = "DELETE FROM company.customers WHERE id = ?";
    private static final String ADD_PROJECT_TO_CUSTOMER = "INSERT INTO company.customerprojects (customerid, projectid, customer_budget) VALUE (?, ?, ?)";
    private static final String DELETE_PROJECT_FROM_CUSROMER = "DELETE FROM company.customerprojects WHERE customerid = ? AND projectid = ?";
    private static final String GET_CUSTOMER_BUDGET_FOR_PROJECT = "SELECT customer_budget FROM company.customerprojects WHERE customerid = ? AND projectid = ? ";

    private ProjectRepository projectRepository;


    public Customer save(Customer customer) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(SAVE_CUSTOMER, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, customer.getName());

        int updated = statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        int customerId = 0;
        if (updated > 0) {
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            customerId = resultSet.getInt(1);
        }

        Util.closeConnection(connection, statement, resultSet);
        if (customerId != 0) {
            customer.setId(customerId);
            return customer;
        }

        return null;
    }

    public Customer get(Integer customerId) throws SQLException {
        Customer customer = null;
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_CUSTOMER_BY_ID);
        statement.setInt(1, customerId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Integer id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            customer = new Customer(id, name);
        }
        Util.closeConnection(connection, statement, resultSet);

        if (customer != null) {
            Map<Project, Integer> projects = projectRepository.getByCustomer(customerId);
            customer.setProjects(projects);
        }

        return customer;
    }

    public List<Customer> getAll() throws SQLException {
        List<Customer> customers = new ArrayList<>();

        Connection connection = Util.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_ALL_CUSTOMERS);
        while (resultSet.next()) {
            Integer id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            customers.add(new Customer(id, name));
        }

        Util.closeConnection(connection, statement, resultSet);
        return customers;
    }

    public boolean update(Customer customer) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_CUSTOMER);
        statement.setString(1, customer.getName());
        statement.setInt(2, customer.getId());
        int updated = statement.executeUpdate();

        Util.closeConnection(connection, statement);

        return updated > 0;
    }

    public boolean delete(Integer customerId) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_CUSTOMER);
        statement.setInt(1, customerId);
        int updated = statement.executeUpdate();

        Util.closeConnection(connection, statement);

        return updated > 0;
    }


    public boolean addProject(Integer customerId, Integer projectId, Integer customerBudget) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(ADD_PROJECT_TO_CUSTOMER);
        statement.setInt(1, customerId);
        statement.setInt(2, projectId);
        statement.setInt(3, customerBudget);
        int newProject = statement.executeUpdate();

        Util.closeConnection(connection, statement);

        Project project = projectRepository.get(projectId);
        project.setCost(project.getCost() + customerBudget);
        projectRepository.updateProject(project);

        return newProject > 0;
    }

    public boolean deleteProject(Integer customerId, Integer projectId) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_CUSTOMER_BUDGET_FOR_PROJECT);
        statement.setInt(1, customerId);
        statement.setInt(2, projectId);
        ResultSet resultSet = statement.executeQuery();
        int customerBudget = 0;
        if (resultSet.next()) {
            customerBudget = resultSet.getInt(1);
        }

        statement = connection.prepareStatement(DELETE_PROJECT_FROM_CUSROMER);
        statement.setInt(1, customerId);
        statement.setInt(2, projectId);
        int deleted = statement.executeUpdate();

        Util.closeConnection(connection, statement, resultSet);

        Project project = projectRepository.get(projectId);
        project.setCost(project.getCost() - customerBudget);
        projectRepository.updateProject(project);

        return deleted > 0;
    }


    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
}
