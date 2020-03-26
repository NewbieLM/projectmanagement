package com.maksym.projectmanagement.repository.hibernate;

import com.maksym.projectmanagement.model.User;
import com.maksym.projectmanagement.repository.UserRepository;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

import static com.maksym.projectmanagement.util.HibernateUtil.getSessionFactory;

public class HibernateUserRepositoryImpl implements UserRepository {

    public List<User> getAll() {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<User> users = session.createQuery("FROM User").getResultList();
        session.getTransaction().commit();
        return users;
    }

    public User get(Integer userId) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM User u JOIN FETCH u.skills WHERE u.id=:id");
        query.setParameter("id", userId);
        User user = (User) query.getSingleResult();
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


    public User update(User user) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.merge(user);
        session.getTransaction().commit();
        return user;
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
