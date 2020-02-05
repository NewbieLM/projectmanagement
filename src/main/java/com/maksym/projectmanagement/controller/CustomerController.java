package com.maksym.projectmanagement.controller;

import com.maksym.projectmanagement.model.Customer;
import com.maksym.projectmanagement.repository.CustomerRepository;

import java.sql.SQLException;
import java.util.List;

public class CustomerController {
    private CustomerRepository customerRepository;

    public Customer save(Customer customer) {
        try {
            customer = customerRepository.save(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public Customer get(Integer customerId) {
        Customer customer = null;
        try {
            customer = customerRepository.get(customerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public List<Customer> getAll() {
        List<Customer> customers = null;
        try {
            customers = customerRepository.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public boolean update(Customer customer) {
        boolean updated = false;
        try {
            updated = customerRepository.update(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updated;
    }

    public boolean delete(Integer customerId) {
        boolean deleted = false;
        try {
            deleted = customerRepository.delete(customerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    public boolean addProject(Integer customerId, Integer customerBudget, Integer projectId) {
        boolean added = false;
        try {
            added = customerRepository.addProject(customerId, customerBudget, projectId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return added;
    }

    public boolean deleteProject(Integer customerId, Integer projectId) {
        boolean deleted = false;
        try {
            deleted = customerRepository.deleteProject(customerId, projectId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
}
