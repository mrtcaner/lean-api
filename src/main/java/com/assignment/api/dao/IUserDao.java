package com.assignment.api.dao;

import com.assignment.api.model.User;
import com.assignment.api.dao.base.IBaseDao;

import java.util.Optional;

public interface IUserDao extends IBaseDao<User> {

    Optional<User> findByEmail(String email);
}
