package com.assignment.controller.utils;

import com.assignment.api.model.Car;

import java.util.ArrayList;
import java.util.List;

public class CarPreparer {

    public static List<Car> prepareCars() {
        List<Car> cars = new ArrayList<>();
        cars.add(Car.builder().latitude(52.537845).longitude(13.424113).maker("Mercedes").model("Home").year(2020).build());
        cars.add(Car.builder().latitude(52.537206).longitude(13.423190).maker("Mercedes").model("70 meters").year(2020).build());
        cars.add(Car.builder().latitude(52.538374).longitude(13.420046).maker("Mercedes").model("930 meters").year(2020).build());
        cars.add(Car.builder().latitude(52.531920).longitude(13.412117).maker("Mercedes").model("out of range").year(2020).build());
        cars.add(Car.builder().latitude(52.536886).longitude(13.420915).maker("Mercedes").model("in range in the middle").year(2020).build());

        return cars;
    }

}
