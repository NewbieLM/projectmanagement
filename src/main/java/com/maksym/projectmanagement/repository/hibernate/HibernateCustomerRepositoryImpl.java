package com.maksym.projectmanagement.repository.hibernate;

import com.maksym.projectmanagement.model.Customer;
import com.maksym.projectmanagement.repository.CustomerRepository;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

import static com.maksym.projectmanagement.util.HibernateUtil.getSessionFactory;


public class HibernateCustomerRepositoryImpl implements CustomerRepository {
    public List<Customer> getAll() {
        List<Customer> customers;
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        customers = session.createQuery("FROM Customer").getResultList();
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

    public Customer update(Customer customer) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.merge(customer);
        session.getTransaction().commit();
        return customer;
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
