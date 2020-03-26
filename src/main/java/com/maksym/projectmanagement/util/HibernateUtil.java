package com.maksym.projectmanagement.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static volatile SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory != null) {
            return sessionFactory;
        }
        synchronized (IOUtil.class) {
            if (sessionFactory == null) {
                sessionFactory = new Configuration().configure("db/hibernate.cfg.xml").buildSessionFactory();
            }
            return sessionFactory;
        }
    }
}
