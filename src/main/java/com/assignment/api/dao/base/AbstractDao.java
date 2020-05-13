package com.assignment.api.dao.base;

import com.assignment.api.utils.Utils;
import com.assignment.api.config.session.DataAccessLayerException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T> implements IBaseDao<T> {

    @Inject
    private Provider<Session> sessionProvider;

    private Session session;
    private Transaction tx;

    private boolean externalTransaction;

    public AbstractDao() {

    }

    public T saveOrUpdate(T obj) {
        try {
            startOperation();
            System.out.println("rentService:" + session.hashCode());
            session.saveOrUpdate(obj);
            if (!externalTransaction)
                tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        }

        return obj;
    }

    public void delete(T obj) {
        try {
            startOperation();
            session.delete(obj);
            if (!externalTransaction)
                tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        }
    }

    public Optional<T> find(Class clazz, Integer id) {
        T obj = null;
        try {
            startOperation();
            obj = (T) session.load(clazz, id);
            if (!externalTransaction)
                tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        }
        return Optional.ofNullable(obj);
    }

    public List<T> findAll(Class clazz) {
        List<T> objects = null;
        try {
            startOperation();
            Query query = session.createQuery("from " + clazz.getName());
            objects = query.list();
            if (!externalTransaction)
                tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        }
        return objects;
    }

    protected void handleException(HibernateException e) throws DataAccessLayerException {
        Utils.rollback(tx);
        throw new DataAccessLayerException(e);
    }

    protected void startOperation() throws HibernateException {

        session = sessionProvider.get();
        tx = session.getTransaction();
        if (tx == null || !tx.isActive()) {
            externalTransaction = false;
            tx = session.beginTransaction();
        } else {
            externalTransaction = true;
        }

    }
}