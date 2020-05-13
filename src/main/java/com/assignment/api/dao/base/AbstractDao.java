package com.assignment.api.dao.base;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T> implements IBaseDao<T> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public T saveOrUpdate(T obj) {
        entityManager.persist(obj);
        return obj;
    }

    @Transactional
    public void delete(T obj) {
        entityManager.remove(obj);
    }

    public Optional<T> find(Class clazz, Integer id) {
        T obj = (T) entityManager.find(clazz, id);
        return Optional.ofNullable(obj);
    }

    public List<T> findAll(Class clazz) {
        Query query = entityManager.createQuery("from " + clazz.getName());
        List<T> objects = query.getResultList();
        return objects;
    }
}