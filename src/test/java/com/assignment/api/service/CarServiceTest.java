package com.assignment.api.service;

import com.assignment.api.model.Car;
import com.assignment.api.service.impl.UserServiceImpl;
import com.assignment.api.dao.impl.CarDaoImpl;
import com.assignment.api.dao.impl.UserDaoImpl;
import com.assignment.api.error.exception.BusinessLogicException;
import com.assignment.api.model.User;
import com.assignment.api.service.impl.CarServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {

    @Mock
    private CarDaoImpl carDao;

    @Mock
    private UserDaoImpl userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @InjectMocks
    private CarServiceImpl carService;

    private Car car;


    @Before
    public void setup() {
        car = new Car();
        car.setId(5);
        car.setLatitude(52.537845);
        car.setLongitude(13.424113);
        car.setMaker("Mercedes");
        car.setModel("CLK Home");
        car.setYear(2020);

        userService = Mockito.spy(new UserServiceImpl());
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void block_whenGivenCarNotBlocked_blockCar() {
        User user = User.builder().id(1).build();
        when(carDao.findByUserId(any())).thenReturn(Optional.ofNullable(null));
        when(carDao.findByIdAndUserIdIsNull(any())).thenReturn(Optional.of(car));
        when(userDao.find(any(), any())).thenReturn(Optional.of(user));
        assertTrue(carService.blockCar(user.getId(), car.getId()));
        assertNotNull(car.getUser());
    }

    @Test(expected = BusinessLogicException.class)
    public void block_whenGivenUserAlreadyBlockedACar_throwsBusinessLogicException() {
        User user = User.builder().id(1).build();
        when(carDao.findByUserId(any())).thenReturn(Optional.of(new Car()));
        carService.blockCar(user.getId(), car.getId());
    }

    @Test(expected = BusinessLogicException.class)
    public void block_whenBlockAlreadyBlockedCar_throwsBusinessLogicException() {
        User user = User.builder().id(1).build();
        when(carDao.findByUserId(any())).thenReturn(Optional.ofNullable(null));
        when(carDao.findByIdAndUserIdIsNull(any())).thenReturn(Optional.ofNullable(null));
        carService.blockCar(user.getId(), car.getId());
    }

    @Test(expected = BusinessLogicException.class)
    public void block_whenGivenInvalidUserId_throwsBusinessLogicException() {
        User user = User.builder().id(1).build();
        when(carDao.findByUserId(any())).thenReturn(Optional.ofNullable(null));
        when(carDao.findByIdAndUserIdIsNull(any())).thenReturn(Optional.of(car));
        when(userDao.find(any(), any())).thenReturn(Optional.ofNullable(null));
        carService.blockCar(user.getId(), car.getId());
    }
}
