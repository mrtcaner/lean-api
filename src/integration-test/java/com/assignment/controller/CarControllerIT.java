package com.assignment.controller;

import com.assignment.api.model.Car;
import com.assignment.api.model.User;
import com.assignment.api.model.dto.UserRegisterDTO;
import com.assignment.controller.utils.CarPreparer;
import com.assignment.controller.utils.UserPreparer;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CarControllerIT extends AbstractBaseControllerIT {

    @Test
    public void car_whenGivenValidCarIdUserId_thenSaveAndReturns201() {
        Car car = CarPreparer.prepareCars().get(0);
        Response response = saveCar(car);

        Car carResp = response.readEntity(Car.class);
        assertEquals(HttpStatus.CREATED_201, response.getStatus());
        assertNotNull(carResp);
        assertEquals(car.getLatitude(), carResp.getLatitude());
        assertEquals(car.getLongitude(), carResp.getLongitude());
        assertEquals(car.getMaker(), carResp.getMaker());
        assertEquals(car.getModel(), carResp.getModel());
        assertEquals(car.getYear(), carResp.getYear());
    }

    @Test
    public void car_whenGivenValidCarIdUserId_thenBlocksAndReturns200() {

        Car car = CarPreparer.prepareCars().get(0);
        //save car
        Response response = saveCar(car);
        Car carSaved = response.readEntity(Car.class);

        UserRegisterDTO registerDTO = UserPreparer.prepareUniqueUser();
        //save user
        response = saveUser(registerDTO);
        User user = response.readEntity(User.class);

        //block car
        response = blockCar(user.getId(), carSaved.getId());
        assertEquals(HttpStatus.OK_200, response.getStatus());

        //get user's blocked car
        response = getUsersBlockedCar(user.getId());
        Car carBlocked = response.readEntity(Car.class);

        assertNotNull(carBlocked);
        assertEquals(carSaved.getId(), carBlocked.getId());

    }

}
