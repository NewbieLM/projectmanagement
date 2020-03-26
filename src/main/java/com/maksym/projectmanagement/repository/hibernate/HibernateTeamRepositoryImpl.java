package com.maksym.projectmanagement.repository.hibernate;

import com.maksym.projectmanagement.model.Team;
import com.maksym.projectmanagement.repository.TeamRepository;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

import static com.maksym.projectmanagement.util.HibernateUtil.getSessionFactory;

public class HibernateTeamRepositoryImpl implements TeamRepository {

    public List<Team> getAll() {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Team> teams = session.createQuery("FROM Team").getResultList();
        session.getTransaction().commit();
        return teams;
    }

    public Team get(Integer teamId) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Team t JOIN FETCH t.users WHERE t.id=:id");
        query.setParameter("id", teamId);
        Team team = (Team) query.getSingleResult();
        session.getTransaction().commit();
        return team;
    }

    public Team save(Team team) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.persist(team);
        session.getTransaction().commit();
        return team;
    }

    public Team update(Team team) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.merge(team);
        session.getTransaction().commit();
        return team;
    }

    public boolean delete(Integer teamId) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM Team t WHERE t.id=:id");
        boolean deleted = query.setParameter("id", teamId).executeUpdate() != 0;
        session.getTransaction().commit();
        return deleted;
    }

}
