package com.assignment.controller;

import com.assignment.controller.utils.UserPreparer;
import com.assignment.api.model.Car;
import com.assignment.api.model.Rent;
import com.assignment.api.model.User;
import com.assignment.api.model.dto.CarSearchResultDTO;
import com.assignment.api.model.dto.UserRegisterDTO;
import com.assignment.api.utils.Constants;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchControllerIT extends AbstractBaseControllerIT {

    private List<Car> prepareCars() {
        List<Car> cars = new ArrayList<>();
        cars.add(Car.builder().latitude(52.537845).longitude(13.424113).maker("Mercedes").model("Home").year(2020).build());
        cars.add(Car.builder().latitude(52.537206).longitude(13.423190).maker("Mercedes").model("70 meters").year(2020).build());
        cars.add(Car.builder().latitude(52.538374).longitude(13.420046).maker("Mercedes").model("930 meters").year(2020).build());
        cars.add(Car.builder().latitude(52.531920).longitude(13.412117).maker("Mercedes").model("out of range").year(2020).build());
        cars.add(Car.builder().latitude(52.536886).longitude(13.420915).maker("Mercedes").model("in range in the middle").year(2020).build());
        return cars;
    }

    @Test
    public void search_whenGivenValidCoordinates_thenReturnsCarList() {

        Double latitude = 52.537845;
        Double longitude = 13.424113;

        prepareCars().stream().forEach(car -> saveCar(car));
        Response response = seachCar(latitude, longitude);
        assertEquals(HttpStatus.OK_200, response.getStatus());

        List<CarSearchResultDTO> nearbyCars = response.readEntity(new GenericType<List<CarSearchResultDTO>>() {
        });

        nearbyCars.forEach(s -> assertTrue(s.getDistance() < Constants.DEFAULT_SEARCH_DIAMETER_IN_KM));
    }

    @Test
    public void search_whenCarBlocked_thenRemovedFromSearchResult() {
        Double latitude = 52.537845;
        Double longitude = 13.424113;

        List<Car> savedCars = new ArrayList<>();
        // save cars
        prepareCars().stream().forEach(car -> {
            savedCars.add(saveCar(car).readEntity(Car.class));
        });

        UserRegisterDTO registerDTO = UserPreparer.prepareUniqueUser();
        //save user
        Response response = saveUser(registerDTO);
        User user = response.readEntity(User.class);

        //get nearby cars
        response = seachCar(latitude, longitude);
        List<CarSearchResultDTO> nearbyCars = response.readEntity(new GenericType<List<CarSearchResultDTO>>() {
        });

        //block car
        response = blockCar(user.getId(), nearbyCars.get(0).getId());

        response = seachCar(latitude, longitude);
        List<CarSearchResultDTO> remainingNearbyCars = response.readEntity(new GenericType<List<CarSearchResultDTO>>() {
        });
        assertTrue(nearbyCars.size() == remainingNearbyCars.size() + 1);

    }

    @Test
    public void rent_whenRentRentEnd_thenCarUnblocked() {

        Double latitude = 52.537845;
        Double longitude = 13.424113;

        List<Car> savedCars = new ArrayList<>();
        // save cars
        prepareCars().stream().forEach(car -> {
            savedCars.add(saveCar(car).readEntity(Car.class));
        });

        UserRegisterDTO registerDTO = UserPreparer.prepareUniqueUser();
        //save user
        Response response = saveUser(registerDTO);
        User user = response.readEntity(User.class);

        //get nearby cars
        response = seachCar(latitude, longitude);
        List<CarSearchResultDTO> nearbyCars = response.readEntity(new GenericType<List<CarSearchResultDTO>>() {
        });

        //block car
        blockCar(user.getId(), nearbyCars.get(0).getId());

        //start rent
        response = startRent(user.getId(), nearbyCars.get(0).getId());

        Rent rent = response.readEntity(Rent.class);

        //end rent
        response = endRent(rent.getId(), user.getId());

        response = seachCar(latitude, longitude);
        List<CarSearchResultDTO> remainingNearbyCars = response.readEntity(new GenericType<List<CarSearchResultDTO>>() {
        });
        assertTrue(remainingNearbyCars.size() == remainingNearbyCars.size());

    }
}
