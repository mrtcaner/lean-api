package com.assignment.api.dao.impl;


import com.assignment.api.dao.IUserDao;
import com.assignment.api.dao.base.AbstractDao;
import com.assignment.api.model.User;

import com.google.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;


public class UserDaoImpl extends AbstractDao<User> implements IUserDao {

    @Inject
    EntityManager entityManager;

    public UserDaoImpl() {
        super();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Query query = entityManager.createNamedQuery("User.findByEmail")
                .setParameter("email", email);

        List<User> userList = query.getResultList();
        if (userList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(userList.get(0));
    }

}

