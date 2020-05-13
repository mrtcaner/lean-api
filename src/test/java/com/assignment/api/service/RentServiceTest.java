package com.assignment.api.service;

import com.assignment.api.model.Car;
import com.assignment.api.model.Rent;
import com.assignment.api.service.impl.UserServiceImpl;
import com.assignment.api.dao.impl.CarDaoImpl;
import com.assignment.api.dao.impl.RentDaoImpl;
import com.assignment.api.dao.impl.UserDaoImpl;
import com.assignment.api.error.exception.BusinessLogicException;
import com.assignment.api.model.User;
import com.assignment.api.service.impl.RentServiceImpl;
import com.assignment.api.utils.Constants;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Provider;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RentServiceTest {

    @Mock
    private CarDaoImpl carDao;

    @Mock
    private UserDaoImpl userDao;

    @Mock
    private RentDaoImpl rentDao;

    @Mock
    Provider<Session> sessionProvider;

    @InjectMocks
    private UserServiceImpl userService;

    @InjectMocks
    private RentServiceImpl rentService;

    private Rent rent;
    private Car car;
    private User user;


    @Before
    public void setup() {
        user = User.builder().id(1).balance(Constants.DEFAULT_USER_BALANCE).build();

        car = new Car();
        car.setId(5);
        car.setLatitude(52.537845);
        car.setLongitude(13.424113);
        car.setMaker("Mercedes");
        car.setModel("CLK Home");
        car.setYear(2020);
        car.setUser(user);

        rent = Rent.builder().car(car).user(user).build();

        userService = Mockito.spy(new UserServiceImpl());
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void rentStart_whenGivenValidRent_start() {
        when(rentDao.findByUserIdAndCarIdAndStartDateIsNotNullAndEndDateIsNull(any(), any())).thenReturn(Optional.ofNullable(null));
        when(carDao.findByIdAndUserId(any(), any())).thenReturn(Optional.of(car));
        when(userDao.find(any(), any())).thenReturn(Optional.of(user));
        Rent rent = rentService.startRent(user.getId(), car.getId());
        assertNotNull(rent);
        assertNotNull(rent.getStartDate());
        assertNotNull(rent.getUser());
        assertNotNull(rent.getCar());
    }

    @Test(expected = BusinessLogicException.class)
    public void rentStart_whenStartAlreadyStarted_throwsBusinessLogicException() {
        when(rentDao.findByUserIdAndCarIdAndStartDateIsNotNullAndEndDateIsNull(any(), any())).thenReturn(Optional.of(rent));
        rentService.startRent(user.getId(), car.getId());

    }

    @Test(expected = BusinessLogicException.class)
    public void rentStart_whenNoBlockedCar_throwsBusinessLogicException() {
        when(rentDao.findByUserIdAndCarIdAndStartDateIsNotNullAndEndDateIsNull(any(), any())).thenReturn(Optional.ofNullable(null));
        when(carDao.findByIdAndUserId(any(), any())).thenReturn(Optional.ofNullable(null));
        rentService.startRent(user.getId(), car.getId());
    }

    @Test(expected = BusinessLogicException.class)
    public void rentStart_whenInvalidUser_throwsBusinessLogicException() {
        when(rentDao.findByUserIdAndCarIdAndStartDateIsNotNullAndEndDateIsNull(any(), any())).thenReturn(Optional.ofNullable(null));
        when(carDao.findByIdAndUserId(any(), any())).thenReturn(Optional.ofNullable(car));
        when(userDao.find(any(), any())).thenReturn(Optional.ofNullable(null));
        rentService.startRent(user.getId(), car.getId());
    }

    @Test
    public void rentEnd_whenValidRent_end() {
        rent.setCost(Constants.DEFAULT_RENT_COST);
        when(rentDao.findByIdAndUserIdAndStartDateIsNotNullAndEndDateIsNull(any(), any())).thenReturn(Optional.ofNullable(rent));
        when(carDao.findByUserId(any())).thenReturn(Optional.ofNullable(car));
        when(userDao.find(any(), any())).thenReturn(Optional.ofNullable(user));
        Session mockSession = mock(Session.class);
        when(mockSession.beginTransaction()).thenReturn(null);
        when(sessionProvider.get()).thenReturn(mockSession);
        rentService.endRent(user.getId(), car.getId());
        assertNull(car.getUser());
        assertNotNull(rent.getEndDate());
        assertTrue(Constants.DEFAULT_USER_BALANCE.equals(user.getBalance() + rent.getCost()));

    }

    @Test(expected = BusinessLogicException.class)
    public void rentEnd_whenInvalidRent_throwsBusinessLogicException() {
        rent.setCost(Constants.DEFAULT_RENT_COST);
        when(rentDao.findByIdAndUserIdAndStartDateIsNotNullAndEndDateIsNull(any(), any())).thenReturn(Optional.ofNullable(null));
        Session mockSession = mock(Session.class);
        when(mockSession.beginTransaction()).thenReturn(null);
        when(sessionProvider.get()).thenReturn(mockSession);
        rentService.endRent(user.getId(), car.getId());

    }

}
