package com.maksym.projectmanagement.repository;

import com.maksym.projectmanagement.model.Skill;
import org.hibernate.Session;

import java.util.List;

import static com.maksym.projectmanagement.util.Util.getSessionFactory;

public class HibernateSkillRepository {

    public List<Skill> getAll() {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Skill> skills = session.createQuery("FROM Skill").list();
        session.getTransaction().commit();
        return skills;
    }

    public Skill get(Integer skillId) {
        Skill skill;
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        skill = session.get(Skill.class, skillId);
        session.getTransaction().commit();
        return skill;
    }

    public Skill saveNewSkill(Skill skill) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.persist(skill);
        session.getTransaction().commit();
        return skill;
    }

    public void updateSkill(Skill skill) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.merge(skill);
        session.getTransaction().commit();
    }
}
