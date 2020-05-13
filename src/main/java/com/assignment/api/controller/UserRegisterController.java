package com.assignment.api.controller;

import com.assignment.api.service.IUserService;
import com.assignment.api.model.dto.UserRegisterDTO;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/")
public class UserRegisterController {

    @Inject
    IUserService userService;

    @POST
    @Path("register")
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(@NotNull UserRegisterDTO userRegisterDTO) {
        return Response.created(URI.create("/")).entity(userService.registerUser(userRegisterDTO)).build();
    }

}
