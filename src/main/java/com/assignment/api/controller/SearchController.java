package com.assignment.api.controller;

import com.assignment.api.service.ICarService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Search for available cars within DEFAULT km diameter
 */
@Path("/search")
public class SearchController {

    @Inject
    ICarService carService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvailableCarList(@QueryParam("latitude") Double latitude, @QueryParam("longitude") Double longitude) {
        return Response.ok(carService.findAvailableCarsWithinDefaultDistance(latitude, longitude)).build();
    }
}
