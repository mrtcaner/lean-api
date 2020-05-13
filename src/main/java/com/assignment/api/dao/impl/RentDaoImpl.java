package com.assignment.api.dao.impl;

import com.assignment.api.dao.IRentDao;
import com.assignment.api.dao.base.AbstractDao;
import com.assignment.api.model.Rent;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.List;
import java.util.Optional;

public class RentDaoImpl extends AbstractDao<Rent> implements IRentDao {

    @Inject
    private Provider<Session> session;

    public RentDaoImpl() {
        super();
    }

    @Override
    public Optional<Rent> findByUserIdAndCarIdAndStartDateIsNotNullAndEndDateIsNull(Integer userId, Integer carId) {
        Query query = session.get()
                .getNamedQuery("Rent.findByUserIdAndCarIdAndStartDateIsNotNullAndEndDateIsNull")
                .setParameter("userId", userId)
                .setParameter("carId", carId);

        List<Rent> rentList = query.list();
        if (rentList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(rentList.get(0));
    }

    @Override
    public Optional<Rent> findByIdAndUserIdAndStartDateIsNotNullAndEndDateIsNull(Integer rentId, Integer userId) {
        Query query = session.get()
                .getNamedQuery("Rent.findByIdAndUserIdAndStartDateIsNotNullAndEndDateIsNull")
                .setParameter("id", rentId)
                .setParameter("userId", userId);

        List<Rent> rentList = query.list();
        if (rentList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(rentList.get(0));
    }

    @Override
    public List<Rent> findByUserId(Integer userId) {
        Query query = session.get().getNamedQuery("Rent.findByUserId")
                .setParameter("userId", userId);

        return query.list();
    }

    public Optional<Rent> findByRentIdUserId(Integer rentId, Integer userId) {
        Query query = session.get().getNamedQuery("Rent.findByRentIdUserId")
                .setParameter("userId", userId)
                .setParameter("id", rentId);

        List<Rent> rentList = query.list();
        if (rentList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(rentList.get(0));
    }
}

