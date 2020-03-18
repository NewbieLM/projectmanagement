package com.maksym.projectmanagement.repository;

import com.maksym.projectmanagement.model.User;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

import static com.maksym.projectmanagement.util.Util.getSessionFactory;

public class HibernateUserRepository {

    public List<User> getAll() {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<User> users = session.createQuery("FROM User").list();
        session.getTransaction().commit();
        return users;
    }

    public User get(Integer usersId) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        User user = session.get(User.class, usersId);
        session.getTransaction().commit();
        return user;
    }

    public User save(User user) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
        return user;
    }


    public void update(User user) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.merge(user);
        session.getTransaction().commit();
    }


    public boolean delete(Integer userId) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM User u WHERE u.id=:id");
        boolean deleted = query.setParameter("id", userId).executeUpdate() != 0;
        session.getTransaction().commit();
        return deleted;
    }
}
