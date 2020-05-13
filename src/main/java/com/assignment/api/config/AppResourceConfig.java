package com.assignment.api.config;

import com.assignment.api.utils.Constants;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class AppResourceConfig extends ResourceConfig {

    public AppResourceConfig() {
    }

    @Inject
    public AppResourceConfig(ServiceLocator serviceLocator) {
        packages(Constants.BASE_PACKAGE);
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        register(JacksonObjectMapper.class);
        register(JacksonJaxbJsonProvider.class);
        Injector injector = Guice.createInjector(new GuiceModule());
        initGuiceIntoHK2Bridge(serviceLocator, injector);
    }

    private void initGuiceIntoHK2Bridge(ServiceLocator serviceLocator, Injector injector) {
        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
        GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
        guiceBridge.bridgeGuiceInjector(injector);
    }


}


