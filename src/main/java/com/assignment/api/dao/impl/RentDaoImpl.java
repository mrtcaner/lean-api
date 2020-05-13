package com.assignment.api.dao.impl;

import com.assignment.api.dao.IRentDao;
import com.assignment.api.dao.base.AbstractDao;
import com.assignment.api.model.Rent;

import com.google.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class RentDaoImpl extends AbstractDao<Rent> implements IRentDao {

    @Inject
    EntityManager entityManager;

    public RentDaoImpl() {
        super();
    }

    @Override
    public Optional<Rent> findByUserIdAndCarIdAndStartDateIsNotNullAndEndDateIsNull(Integer userId, Integer carId) {
        Query query = entityManager.createNamedQuery("Rent.findByUserIdAndCarIdAndStartDateIsNotNullAndEndDateIsNull")
                .setParameter("userId", userId)
                .setParameter("carId", carId);

        List<Rent> rentList = query.getResultList();
        if (rentList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(rentList.get(0));
    }

    @Override
    public Optional<Rent> findByIdAndUserIdAndStartDateIsNotNullAndEndDateIsNull(Integer rentId, Integer userId) {
        Query query = entityManager.createNamedQuery("Rent.findByIdAndUserIdAndStartDateIsNotNullAndEndDateIsNull")
                .setParameter("id", rentId)
                .setParameter("userId", userId);

        List<Rent> rentList = query.getResultList();
        if (rentList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(rentList.get(0));
    }

    @Override
    public List<Rent> findByUserId(Integer userId) {
        Query query = entityManager.createNamedQuery("Rent.findByUserId")
                .setParameter("userId", userId);

        return query.getResultList();
    }

    public Optional<Rent> findByRentIdUserId(Integer rentId, Integer userId) {
        Query query = entityManager.createNamedQuery("Rent.findByRentIdUserId")
                .setParameter("userId", userId)
                .setParameter("id", rentId);

        List<Rent> rentList = query.getResultList();
        if (rentList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(rentList.get(0));
    }
}

