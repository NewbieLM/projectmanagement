package com.maksym.projectmanagement.controller;

import com.maksym.projectmanagement.model.Customer;
import com.maksym.projectmanagement.repository.HibernateCustomerRepository;

import java.util.List;

public class CustomerController {
    private HibernateCustomerRepository customerRepository;

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer get(Integer customerId) {
        return customerRepository.get(customerId);
    }

    public List<Customer> getAll() {
        return customerRepository.getAll();
    }

    public void update(Customer customer) {
        customerRepository.update(customer);
    }

    public boolean delete(Integer customerId) {
        return customerRepository.delete(customerId);
    }


    public void setCustomerRepository(HibernateCustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
}
