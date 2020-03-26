package com.maksym.projectmanagement.controller;

import com.maksym.projectmanagement.model.Customer;
import com.maksym.projectmanagement.repository.hibernate.HibernateCustomerRepositoryImpl;

import java.util.List;

public class CustomerController {
    private HibernateCustomerRepositoryImpl hibernateCustomerRepositoryImpl;

    public Customer save(Customer customer) {
        return hibernateCustomerRepositoryImpl.save(customer);
    }

    public Customer get(Integer customerId) {
        return hibernateCustomerRepositoryImpl.get(customerId);
    }

    public List<Customer> getAll() {
        return hibernateCustomerRepositoryImpl.getAll();
    }

    public void update(Customer customer) {
        hibernateCustomerRepositoryImpl.update(customer);
    }

    public boolean delete(Integer customerId) {
        return hibernateCustomerRepositoryImpl.delete(customerId);
    }


    public void setHibernateCustomerRepositoryImpl(HibernateCustomerRepositoryImpl hibernateCustomerRepositoryImpl) {
        this.hibernateCustomerRepositoryImpl = hibernateCustomerRepositoryImpl;
    }
}
