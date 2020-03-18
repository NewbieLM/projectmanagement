package com.maksym.projectmanagement.repository;

import com.maksym.projectmanagement.model.Project;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static com.maksym.projectmanagement.util.Util.getSessionFactory;

public class HibernateProjectRepository {

    public List<Project> getAll() {
        List<Project> projects;
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        projects = session.createQuery("FROM Project").list();
        session.getTransaction().commit();
        return projects;
    }

    public Project get(Integer projectId) {
        Project project;
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        project = session.get(Project.class, projectId);
        Hibernate.initialize(project.getTeams());
        session.getTransaction().commit();
        return project;
    }

    public Project save(Project project) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.persist(project);
        session.getTransaction().commit();
        return project;
    }

    public void updateProject(Project project) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.merge(project);
        session.getTransaction().commit();
    }

    public boolean delete(Integer projectId) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM Project p WHERE p.id=:id");
        boolean deleted = query.setParameter("id", projectId).executeUpdate() != 0;
        session.getTransaction().commit();
        return deleted;
    }
}
