package com.gestao.livros.gestaolivros.service;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class Dao  {

    @PersistenceContext
    private EntityManager entityManager;


    public Session getSession() {
        if (entityManager != null) {
            Session session = entityManager.unwrap(Session.class);
            if (!session.isOpen()) {
                return session.getSessionFactory().openSession();
            }
            return session;
        }
        return null;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }
}
