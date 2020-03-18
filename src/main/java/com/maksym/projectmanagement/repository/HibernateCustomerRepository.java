package com.maksym.projectmanagement.repository;

import com.maksym.projectmanagement.model.Customer;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static com.maksym.projectmanagement.util.Util.getSessionFactory;


public class HibernateCustomerRepository {
    public List<Customer> getAll() {
        List<Customer> customers;
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        customers = session.createQuery("FROM Customer").list();
        session.getTransaction().commit();
        return customers;
    }

    public Customer get(Integer customerId) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Customer customer = session.get(Customer.class, customerId);
        Hibernate.initialize(customer.getProjects());
        session.getTransaction().commit();
        return customer;
    }

    public Customer save(Customer customer) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.persist(customer);
        session.getTransaction().commit();
        return customer;
    }

    public void update(Customer customer) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.merge(customer);
        session.getTransaction().commit();
    }

    public boolean delete(Integer customerId) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM Customer p WHERE p.id=:id");
        boolean deleted = query.setParameter("id", customerId).executeUpdate() != 0;
        session.getTransaction().commit();
        return deleted;
    }

}
