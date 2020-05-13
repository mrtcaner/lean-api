package com.assignment.api.service;

import com.assignment.api.model.Car;
import com.assignment.api.model.dto.CarSearchResultDTO;

import java.util.List;
import java.util.Optional;

public interface ICarService {

    public List<CarSearchResultDTO> findAvailableCarsWithinDefaultDistance(Double latitude, Double longitude);

    public boolean blockCar(Integer userId, Integer carId);

    public void checkAndTrowExceptionIfUserAlreadyBlockedACar(Integer userId);

    public Car findByIdAndUserIdIsNullOrThrowException(Integer carId);

    public Optional<Car> getBlockedCar(Integer userId);

    Integer saveOrUpdate(Car car);
}
