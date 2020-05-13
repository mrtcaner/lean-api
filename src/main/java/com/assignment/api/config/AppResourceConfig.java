package com.assignment.api.config;

import com.assignment.api.utils.Constants;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class AppResourceConfig extends ResourceConfig {

    public AppResourceConfig() {

        packages(Constants.BASE_PACKAGE);
        register(new ApiApplicationBinder());
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        register(JacksonObjectMapper.class);
        register(JacksonJaxbJsonProvider.class);
    }


}


