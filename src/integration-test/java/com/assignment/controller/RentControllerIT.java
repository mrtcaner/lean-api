package com.assignment.controller;

import com.assignment.controller.utils.CarPreparer;
import com.assignment.controller.utils.UserPreparer;
import com.assignment.api.model.Car;
import com.assignment.api.model.Rent;
import com.assignment.api.model.User;
import com.assignment.api.model.dto.UserRegisterDTO;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.*;

public class RentControllerIT extends AbstractBaseControllerIT {

    @Test
    public void rent_whenGivenValidCarIdUserId_thenRentStartAndReturnsRentWith201() {

        //save car
        Car car = CarPreparer.prepareCars().get(0);
        Response response = saveCar(car);
        car = response.readEntity(Car.class);

        //save user
        UserRegisterDTO registerDTO = UserPreparer.prepareUniqueUser();
        response = saveUser(registerDTO);
        User user = response.readEntity(User.class);

        //block car
        blockCar(user.getId(), car.getId());

        //start rent
        response = startRent(user.getId(), car.getId());

        assertEquals(HttpStatus.CREATED_201, response.getStatus());

        Rent rent = response.readEntity(Rent.class);
        assertNotNull(rent);
        assertEquals(rent.getUser().getId(), user.getId());
        assertEquals(rent.getCar().getId(), car.getId());
        assertNotNull(rent.getStartDate());
        assertNull(rent.getEndDate());
    }

    @Test
    public void rent_whenGivenValidRentIdUserId_thenRentEndAndReturns200() {

        //save car
        Car car = CarPreparer.prepareCars().get(0);
        Response response = saveCar(car);
        car = response.readEntity(Car.class);

        //save user
        UserRegisterDTO registerDTO = UserPreparer.prepareUniqueUser();
        response = saveUser(registerDTO);
        User user = response.readEntity(User.class);

        //block car
        blockCar(user.getId(), car.getId());

        //start rent
        response = startRent(user.getId(), car.getId());

        Rent rent = response.readEntity(Rent.class);

        //end rent
        response = endRent(rent.getId(), user.getId());
        assertEquals(HttpStatus.OK_200, response.getStatus());

        //get user's rents
        Response response1 = getUsersRents(user.getId());
        List<Rent> rentList = response1.readEntity(new GenericType<List<Rent>>() {});

        assertEquals(rentList.size(), 1);
        Rent rentResp = rentList.get(0);
        assertEquals(rentResp.getId(), rent.getId());
        assertEquals(rentResp.getUser().getId(), user.getId());
        assertEquals(rentResp.getCar().getId(), car.getId());
        assertNotNull(rentResp.getStartDate());
        assertNotNull(rentResp.getEndDate());

    }
}
