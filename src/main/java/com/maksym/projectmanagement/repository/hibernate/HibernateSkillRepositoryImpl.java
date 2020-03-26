package com.maksym.projectmanagement.repository.hibernate;

import com.maksym.projectmanagement.model.Skill;
import com.maksym.projectmanagement.repository.SkillRepository;
import org.hibernate.Session;

import java.util.List;

import static com.maksym.projectmanagement.util.HibernateUtil.getSessionFactory;

public class HibernateSkillRepositoryImpl implements SkillRepository {

    public List<Skill> getAll() {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Skill> skills = session.createQuery("FROM Skill").getResultList();
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

    public Skill save(Skill skill) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.persist(skill);
        session.getTransaction().commit();
        return skill;
    }

    public Skill update(Skill skill) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.merge(skill);
        session.getTransaction().commit();
        return skill;
    }

    @Override
    public boolean delete(Integer integer) {
        //NOP
        return false;
    }
}
