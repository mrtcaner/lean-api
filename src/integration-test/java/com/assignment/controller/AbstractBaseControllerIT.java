package com.assignment.controller;

import com.assignment.api.config.AppResourceConfig;
import com.assignment.api.config.JacksonObjectMapper;
import com.assignment.api.model.Car;
import com.assignment.api.model.dto.UserRegisterDTO;
import com.assignment.api.utils.Constants;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public abstract class AbstractBaseControllerIT extends JerseyTest {

    @Override
    protected Application configure() {
        return new AppResourceConfig().packages(Constants.BASE_PACKAGE);
    }

    @Override
    protected DeploymentContext configureDeployment() {
        return DeploymentContext.builder(AppResourceConfig.class).build();
    }

    @Override
    protected void configureClient(final ClientConfig config) {
        config.register(JacksonObjectMapper.class);
        config.register(JacksonJaxbJsonProvider.class);
    }


    protected Response saveUser(UserRegisterDTO userRegisterDTO) {
        Response response = target("/register").request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(userRegisterDTO, MediaType.APPLICATION_JSON_TYPE));
        return response;
    }

    protected Response saveCar(Car car) {
        Response response = target("/car").request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(car, MediaType.APPLICATION_JSON_TYPE));
        return response;
    }

    protected Response blockCar(Integer userId, Integer carId) {
        Response response = target("/car")
                .path(userId.toString()).path(carId.toString()).request().put(Entity.json(""));
        return response;
    }

    protected Response getUsersBlockedCar(Integer userId) {
        Response response = target("/car").path(userId.toString()).request().get();
        return response;
    }

    protected Response startRent(Integer userId, Integer carId) {
        Response response = target("/rent")
                .path(userId.toString()).path(carId.toString()).request().post(Entity.json(""));
        return response;
    }

    protected Response endRent(Integer rentId, Integer userId) {
        Response response = target("/rent")
                .path(rentId.toString()).path(userId.toString()).request().put(Entity.json(""));
        return response;
    }

    protected Response getUsersRents(Integer userId) {
        Response response = target("/rent").path(userId.toString()).request().get();
        return response;
    }

    protected Response seachCar(Double latitude, Double longitude) {
        Response response = target("/search").queryParam("latitude", latitude)
                .queryParam("longitude", longitude).request()
                .get();
        return response;
    }
}
