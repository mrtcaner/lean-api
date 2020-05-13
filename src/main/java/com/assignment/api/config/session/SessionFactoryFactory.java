package com.assignment.api.config.session;

import org.glassfish.hk2.api.Factory;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class SessionFactoryFactory implements Factory<SessionFactory> {

    private final SessionFactory factory;

    public SessionFactoryFactory() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        StandardServiceRegistryBuilder srBuilder = new StandardServiceRegistryBuilder();
        srBuilder.applySettings(configuration.getProperties());
        factory = configuration.buildSessionFactory(srBuilder.build());
    }

    public SessionFactory provide() {
        return factory;
    }

    @Override
    public void dispose(SessionFactory factory) {
        factory.close();
    }
}