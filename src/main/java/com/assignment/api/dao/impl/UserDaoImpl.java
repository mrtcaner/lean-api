package com.assignment.api.dao.impl;


import com.assignment.api.model.User;
import com.assignment.api.dao.IUserDao;
import com.assignment.api.dao.base.AbstractDao;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.List;
import java.util.Optional;


public class UserDaoImpl extends AbstractDao<User> implements IUserDao {

    @Inject
    private Provider<Session> session;

    public UserDaoImpl() {
        super();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Query query = session.get().getNamedQuery("User.findByEmail")
                .setParameter("email", email);

        List<User> userList = query.list();
        if (userList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(userList.get(0));
    }

}

