package com.assignment.api.dao.base;

import java.util.List;
import java.util.Optional;

public interface IBaseDao<T> {

    T saveOrUpdate(T obj);

    void delete(T obj);

    Optional<T> find(Class clazz, Integer id);

    List<T> findAll(Class clazz);
}
