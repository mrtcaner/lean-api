package com.assignment.api.config.session;

import org.glassfish.hk2.api.Factory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.inject.Inject;

public class SFFactory implements Factory<Session> {

    private final SessionFactory factory;

    @Inject
    public SFFactory(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Session provide() {
        return factory.openSession();
    }

    @Override
    public void dispose(Session session) {
        commit(session.getTransaction());
        close(session);
    }

    private static void close(Session session) {
        if (session != null && session.isOpen()) {
            try {
                session.close();
            } catch (HibernateException ignored) {
                System.err.println("Couldn't close Session:" + ignored);
            }
        }
    }

    private static void commit(Transaction tx) {
        try {
            if (tx != null && tx.isActive()) {
                tx.commit();
            }
        } catch (HibernateException ignored) {
            System.err.println("Couldn't commit Transaction:" + ignored);
        }
    }
}