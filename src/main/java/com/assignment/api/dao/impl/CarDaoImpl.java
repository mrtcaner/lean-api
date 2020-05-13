package com.assignment.api.dao.impl;

import com.assignment.api.dao.ICarDao;
import com.assignment.api.dao.base.AbstractDao;
import com.assignment.api.model.Car;
import com.assignment.api.model.dto.CarSearchResultDTO;
import com.assignment.api.utils.Constants;
import com.google.inject.Inject;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;


public class CarDaoImpl extends AbstractDao<Car> implements ICarDao {

    @Inject
    EntityManager entityManager;

    public CarDaoImpl() {
        super();
    }

    @Override
    public Optional<Car> findByIdAndUserIdIsNull(Integer carId) {
        Query query = entityManager.createNamedQuery("Car.findByIdAndUserIdIsNull")
                .setParameter("id", carId);

        List<Car> carList = query.getResultList();
        if (carList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(carList.get(0));
    }

    @Override
    public Optional<Car> findByIdAndUserId(Integer carId, Integer userId) {
        Query query = entityManager.createNamedQuery("Car.findByIdAndUserId")
                .setParameter("id", carId)
                .setParameter("userId", userId);

        List<Car> carList = query.getResultList();
        if (carList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(carList.get(0));
    }

    @Override
    public Optional<Car> findByUserId(Integer userId) {
        Query query = entityManager.createNamedQuery("Car.findByUserId")
                .setParameter("userId", userId);

        List<Car> carList = query.getResultList();
        if (carList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(carList.get(0));
    }

    @Override
    public List<CarSearchResultDTO> findAvailableCarsWithinDefaultDistance(Double latitude, Double longitude) {
        Query query = entityManager.createNamedQuery("Car.findAvailableCarsWithinDefaultDistance")
                .setParameter("latitude", latitude)
                .setParameter("longitude", longitude)
                .setParameter("diameter", Constants.DEFAULT_SEARCH_DIAMETER_IN_KM);

        List<CarSearchResultDTO> results = query.getResultList();
        return results;
    }
}

