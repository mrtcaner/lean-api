package com.assignment.api.dao;

import com.assignment.api.model.Car;
import com.assignment.api.model.dto.CarSearchResultDTO;
import com.assignment.api.dao.base.IBaseDao;

import java.util.List;
import java.util.Optional;

public interface ICarDao extends IBaseDao<Car> {

    Optional<Car> findByIdAndUserIdIsNull(Integer carId);

    Optional<Car> findByIdAndUserId(Integer carId, Integer userId);

    Optional<Car> findByUserId(Integer userId);

    List<CarSearchResultDTO> findAvailableCarsWithinDefaultDistance(Double latitude, Double longitude);


}
