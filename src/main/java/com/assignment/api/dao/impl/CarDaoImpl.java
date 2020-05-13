package com.assignment.api.dao.impl;

import com.assignment.api.dao.ICarDao;
import com.assignment.api.dao.base.AbstractDao;
import com.assignment.api.model.Car;
import com.assignment.api.model.dto.CarSearchResultDTO;
import com.assignment.api.utils.Constants;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.List;
import java.util.Optional;


public class CarDaoImpl extends AbstractDao<Car> implements ICarDao {

    @Inject
    private Provider<Session> session;

    public CarDaoImpl() {
        super();
    }

    @Override
    public Optional<Car> findByIdAndUserIdIsNull(Integer carId) {
        Query query = session.get().getNamedQuery("Car.findByIdAndUserIdIsNull")
                .setParameter("id", carId);

        List<Car> carList = query.list();
        if (carList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(carList.get(0));
    }

    @Override
    public Optional<Car> findByIdAndUserId(Integer carId, Integer userId) {
        Query query = session.get().getNamedQuery("Car.findByIdAndUserId")
                .setParameter("id", carId)
                .setParameter("userId", userId);

        List<Car> carList = query.list();
        if (carList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(carList.get(0));
    }

    @Override
    public Optional<Car> findByUserId(Integer userId) {
        Query query = session.get().getNamedQuery("Car.findByUserId")
                .setParameter("userId", userId);

        List<Car> carList = query.list();
        if (carList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(carList.get(0));
    }

    @Override
    public List<CarSearchResultDTO> findAvailableCarsWithinDefaultDistance(Double latitude, Double longitude) {
        Query query = session.get().getNamedQuery("Car.findAvailableCarsWithinDefaultDistance")
                .setParameter("latitude", latitude)
                .setParameter("longitude", longitude)
                .setParameter("diameter", Constants.DEFAULT_SEARCH_DIAMETER_IN_KM);

        List<CarSearchResultDTO> results = query.list();
        return results;
    }
}

