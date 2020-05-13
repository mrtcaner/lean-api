package com.assignment.api.service.impl;

import com.assignment.api.error.enums.ErrorEnum;
import com.assignment.api.model.Car;
import com.assignment.api.model.dto.CarSearchResultDTO;
import com.assignment.api.service.IUserService;
import com.assignment.api.dao.ICarDao;
import com.assignment.api.error.exception.BusinessLogicException;
import com.assignment.api.service.ICarService;
import org.hibernate.Session;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public class CarServiceImpl implements ICarService {

    @Inject
    IUserService userService;

    @Inject
    ICarDao carDao;

    @Inject
    Provider<Session> sessionProvider;

    public List<CarSearchResultDTO> findAvailableCarsWithinDefaultDistance(Double latitude, Double longitude) {
        return carDao.findAvailableCarsWithinDefaultDistance(latitude, longitude);
    }

    public boolean blockCar(@NotNull Integer userId, @NotNull Integer carId) {
        checkAndTrowExceptionIfUserAlreadyBlockedACar(userId);
        Car car = findByIdAndUserIdIsNullOrThrowException(carId);
        car.setUser(userService.findUserOrThrowException(userId));
        carDao.saveOrUpdate(car);
        return true;

    }

    public void checkAndTrowExceptionIfUserAlreadyBlockedACar(Integer userId) {
        carDao.findByUserId(userId).ifPresent(s -> {
            throw new BusinessLogicException(ErrorEnum.CANNOT_BLOCK_MORE_THAN_ONE.getCode(),
                    ErrorEnum.CANNOT_BLOCK_MORE_THAN_ONE.getMessage());
        });
    }

    public Car findByIdAndUserIdIsNullOrThrowException(Integer carId) {
        Optional<Car> carOpt = carDao.findByIdAndUserIdIsNull(carId);
        carOpt.orElseThrow(() -> new BusinessLogicException(ErrorEnum.CAR_NO_LONGER_AVAILABLE.getCode(),
                ErrorEnum.CAR_NO_LONGER_AVAILABLE.getMessage()));
        return carOpt.get();
    }

    public Optional<Car> getBlockedCar(Integer userId) {
        return carDao.findByUserId(userId);
    }

    @Override
    public Integer saveOrUpdate(Car car) {
        carDao.saveOrUpdate(car);
        return car.getId();
    }

}
