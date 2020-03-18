package com.maksym.projectmanagement.repository;

import com.maksym.projectmanagement.model.Team;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

import static com.maksym.projectmanagement.util.Util.getSessionFactory;

public class HibernateTeamRepository {

    public List<Team> getAll() {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Team> teams = session.createQuery("FROM Team").list();
        session.getTransaction().commit();
        return teams;
    }

    public Team get(Integer teamId) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Team team = session.get(Team.class, teamId);
        Hibernate.initialize(team.getUsers());
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

    public void update(Team team) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.merge(team);
        session.getTransaction().commit();
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
