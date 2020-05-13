package com.assignment.api.service;

import com.assignment.api.model.Car;
import com.assignment.api.model.dto.CarSearchResultDTO;

import java.util.List;
import java.util.Optional;

public interface ICarService {

    List<CarSearchResultDTO> findAvailableCarsWithinDefaultDistance(Double latitude, Double longitude);

    boolean blockCar(Integer userId, Integer carId);

    void checkAndTrowExceptionIfUserAlreadyBlockedACar(Integer userId);

    Car findByIdAndUserIdIsNullOrThrowException(Integer carId);

    Optional<Car> getBlockedCar(Integer userId);

    Integer saveOrUpdate(Car car);
}
