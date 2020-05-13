package com.assignment.api.config;

import com.assignment.api.service.IUserService;
import com.assignment.api.service.impl.UserServiceImpl;
import com.assignment.api.config.aop.HK2InterceptionService;
import com.assignment.api.config.aop.TransactionalResourceInterceptor;
import com.assignment.api.config.session.SFFactory;
import com.assignment.api.config.session.SessionFactoryFactory;
import com.assignment.api.dao.ICarDao;
import com.assignment.api.dao.IRentDao;
import com.assignment.api.dao.IUserDao;
import com.assignment.api.dao.impl.CarDaoImpl;
import com.assignment.api.dao.impl.RentDaoImpl;
import com.assignment.api.dao.impl.UserDaoImpl;
import com.assignment.api.service.ICarService;
import com.assignment.api.service.IRentService;
import com.assignment.api.service.impl.CarServiceImpl;
import com.assignment.api.service.impl.RentServiceImpl;
import org.glassfish.hk2.api.JustInTimeInjectionResolver;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Singleton;


public class ApiApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(JustInTimeServiceResolver.class).to(JustInTimeInjectionResolver.class);

        bind(HK2InterceptionService.class)
                .to(org.glassfish.hk2.api.InterceptionService.class)
                .in(Singleton.class);

        bind(TransactionalResourceInterceptor.class);

        bind(UserServiceImpl.class).to(IUserService.class);
        bind(UserDaoImpl.class).to(IUserDao.class).in(RequestScoped.class);
        bind(CarServiceImpl.class).to(ICarService.class);
        bind(CarDaoImpl.class).to(ICarDao.class).in(RequestScoped.class);
        bind(RentServiceImpl.class).to(IRentService.class);
        bind(RentDaoImpl.class).to(IRentDao.class).in(RequestScoped.class);

        bindFactory(SessionFactoryFactory.class)
                .to(SessionFactory.class)
                .in(Singleton.class);
        bindFactory(SFFactory.class)
                .to(Session.class)
                .in(RequestScoped.class);
    }
}