package com.assignment.api.controller;

import com.assignment.api.model.Car;
import com.assignment.api.service.ICarService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Optional;

/**
 * Block car, unblock car ...
 */
@Path("/car")
public class CarController {

    @Inject
    ICarService carService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCar(Car car) {
        carService.saveOrUpdate(car);
        return Response.created(URI.create("")).entity(car).build();
    }

    @PUT
    @Path("/{userId}/{carId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response blockCar(@PathParam("userId") Integer userId, @PathParam("carId") Integer carId) {
        carService.blockCar(userId, carId);
        return Response.ok().build();
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBlockedCar(@PathParam("userId") Integer userId) {
        Optional<Car> carOpt = carService.getBlockedCar(userId);
        if (carOpt.isPresent()) {
            return Response.ok(carOpt.get()).build();
        }
        return Response.ok().build();
    }


}
