package com.maksym.projectmanagement.repository.hibernate;

import com.maksym.projectmanagement.model.Project;
import com.maksym.projectmanagement.repository.ProjectRepository;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

import static com.maksym.projectmanagement.util.HibernateUtil.getSessionFactory;

public class HibernateProjectRepositoryImpl implements ProjectRepository {

    public List<Project> getAll() {
        List<Project> projects;
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        projects = session.createQuery("FROM Project").getResultList();
        session.getTransaction().commit();
        return projects;
    }

    public Project get(Integer projectId) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Project p JOIN FETCH p.teams WHERE p.id=:id");
        query.setParameter("id", projectId);
        Project project = (Project) query.getSingleResult();
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

    public Project update(Project project) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.merge(project);
        session.getTransaction().commit();
        return project;
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
