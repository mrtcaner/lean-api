package com.assignment.api.config;

import com.assignment.api.config.persistence.JPAInitializer;
import com.assignment.api.dao.ICarDao;
import com.assignment.api.dao.IRentDao;
import com.assignment.api.dao.IUserDao;
import com.assignment.api.dao.impl.CarDaoImpl;
import com.assignment.api.dao.impl.RentDaoImpl;
import com.assignment.api.dao.impl.UserDaoImpl;
import com.assignment.api.service.ICarService;
import com.assignment.api.service.IRentService;
import com.assignment.api.service.IUserService;
import com.assignment.api.service.impl.CarServiceImpl;
import com.assignment.api.service.impl.RentServiceImpl;
import com.assignment.api.service.impl.UserServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;

public class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new JpaPersistModule("api-persister"));
        bind(JPAInitializer.class).asEagerSingleton();
        bind(IUserService.class).to(UserServiceImpl.class);
        bind(IUserDao.class).to(UserDaoImpl.class);
        bind(ICarService.class).to(CarServiceImpl.class);
        bind(ICarDao.class).to(CarDaoImpl.class);
        bind(IRentService.class).to(RentServiceImpl.class);
        bind(IRentDao.class).to(RentDaoImpl.class);

    }
}