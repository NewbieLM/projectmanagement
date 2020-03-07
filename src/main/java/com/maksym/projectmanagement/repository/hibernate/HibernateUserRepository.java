package com.maksym.projectmanagement.repository.hibernate;

import com.maksym.projectmanagement.model.User;
import org.hibernate.Session;

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
        session.merge(user);
        session.getTransaction().commit();
        return user;
    }

    ///BOOLEAN
    public void update(User user) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.merge(user);
        session.getTransaction().commit();
    }

    ///BOOLEAN
    public void delete(Integer userId) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        User user = new User("deleted_user");
        user.setId(userId);
        session.delete(user);
        session.getTransaction().commit();
    }
}
