package com.assignment.api.service.impl;

import com.assignment.api.dao.ICarDao;
import com.assignment.api.dao.IRentDao;
import com.assignment.api.dao.IUserDao;
import com.assignment.api.error.enums.ErrorEnum;
import com.assignment.api.error.exception.BusinessLogicException;
import com.assignment.api.model.Car;
import com.assignment.api.model.Rent;
import com.assignment.api.model.User;
import com.assignment.api.service.IRentService;
import com.assignment.api.service.IUserService;
import com.assignment.api.utils.Constants;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class RentServiceImpl implements IRentService {

    @Inject
    IRentDao rentRepository;

    @Inject
    IUserService userService;

    @Inject
    ICarDao carRepository;

    @Inject
    IUserDao userRepository;

    @Inject
    EntityManager entityManager;

    public Rent startRent(Integer userId, Integer carId) {
        checkAndThrowExceptionIfAlreadyStarted(userId, carId);
        Rent rent = Rent.builder()
                .car(findBlockedCarThrowExceptionIfNotExist(carId, userId))
                .user(userService.findUserOrThrowException(userId))
                .startDate(LocalDateTime.now())
                .cost(Constants.DEFAULT_RENT_COST)
                .build();
        rentRepository.saveOrUpdate(rent);
        return rent;
    }


    private void checkAndThrowExceptionIfAlreadyStarted(Integer userId, Integer carId) {
        //started date not null end date null
        rentRepository.findByUserIdAndCarIdAndStartDateIsNotNullAndEndDateIsNull(userId, carId).ifPresent(s -> {
            throw new BusinessLogicException(ErrorEnum.RENT_ALREADY_STARTED.getCode(),
                    ErrorEnum.RENT_ALREADY_STARTED.getMessage());
        });
    }

    private Car findBlockedCarThrowExceptionIfNotExist(Integer carId, Integer userId) {
        Optional<Car> carOpt = carRepository.findByIdAndUserId(carId, userId);
        carOpt.orElseThrow(() -> new BusinessLogicException(ErrorEnum.USER_NO_BLOCKED_CAR.getCode(),
                ErrorEnum.USER_NO_BLOCKED_CAR.getMessage()));
        return carOpt.get();
    }

    @Transactional
    public Rent endRent(Integer rentId, Integer userId) {
        Rent rent = findStartedRentThrowExceptionIfNotExist(rentId, userId);
        rent.setEndDate(LocalDateTime.now());
        rentRepository.saveOrUpdate(rent);
        findCarByUserIdAndMakeAvailable(userId);
        findUserAndDeductCost(userId, rent.getCost());
        return rent;
    }

    private void findCarByUserIdAndMakeAvailable(Integer userId) {
        Optional<Car> carOpt = carRepository.findByUserId(userId);
        carOpt.ifPresent(s -> {
            s.setUser(null);
            carRepository.saveOrUpdate(s);
        });
    }

    private void findUserAndDeductCost(Integer userId, double cost) {
        Optional<User> userOpt = userRepository.find(User.class, userId);
        userOpt.ifPresent(s -> {
            s.setBalance(s.getBalance() - cost);
            userRepository.saveOrUpdate(s);
        });
    }

    private Rent findStartedRentThrowExceptionIfNotExist(Integer rentId, Integer userId) {
        //started date not null end date null
        Optional<Rent> rentOpt = rentRepository.findByIdAndUserIdAndStartDateIsNotNullAndEndDateIsNull(rentId, userId);
        rentOpt.orElseThrow(() -> new BusinessLogicException(ErrorEnum.NO_SUCH_RENT_ALREADY_ENDED.getCode(),
                ErrorEnum.NO_SUCH_RENT_ALREADY_ENDED.getMessage()));
        return rentOpt.get();
    }

    public List<Rent> getUsersRents(Integer userId) {
        return rentRepository.findByUserId(userId);
    }

    public Rent findByRentIdUserId(Integer rentId, Integer userId) {
        Optional<Rent> rentOptional = rentRepository.findByRentIdUserId(rentId, userId);
        return rentOptional.isPresent() ? rentOptional.get() : null;
    }


}
