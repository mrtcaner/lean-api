package com.assignment.api.controller;

import com.assignment.api.model.Rent;
import com.assignment.api.service.IRentService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * Start rent, stop rent, get cost
 */
@Path("/rent")
public class RentController {

    @Inject
    IRentService rentService;

    @POST
    @Path("/{userId}/{carId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response startRent(@PathParam("userId") Integer userId, @PathParam("carId") Integer carId) {
        Rent rent = rentService.startRent(userId, carId);
        return Response.created(URI.create("/rent/" + rent.getId() + "/" + userId)).entity(rent).build();
    }

    @PUT
    @Path("/{rentId}/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response endRent(@PathParam("rentId") Integer rentId, @PathParam("userId") Integer userId) {
        rentService.endRent(rentId, userId);
        return Response.ok().build();
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRentedCarList(@PathParam("userId") Integer userId) {
        return Response.ok(rentService.getUsersRents(userId)).build();
    }

    @GET
    @Path("/{rentId}/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRent(@PathParam("rentId") Integer rentId, @PathParam("userId") Integer userId) {
        return Response.ok(rentService.findByRentIdUserId(rentId, userId)).build();
    }

}
