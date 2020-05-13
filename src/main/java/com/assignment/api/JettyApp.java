package com.assignment.api;

import com.assignment.api.config.AppResourceConfig;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class JettyApp {

    public static void main(String[] args) throws Exception {
        // Jersey
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        ServletContainer jerseyServletContainer = new ServletContainer(new AppResourceConfig());
        ServletHolder jerseyServletHolder = new ServletHolder(jerseyServletContainer);
        servletContextHandler.setContextPath("/");
        servletContextHandler.addServlet(jerseyServletHolder, "/*");

        // Wire up Jetty
        HandlerCollection handlerList = new HandlerCollection();
        handlerList.setHandlers(new Handler[]{servletContextHandler});
        Server server = new Server(8080);
        server.setHandler(handlerList);
        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }
}
