package com.mzhang.restaurant.config;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.mzhang.restaurant.controller.ReservationController;
import com.mzhang.restaurant.model.Reservation;
import com.mzhang.restaurant.repository.ReservationRepository;
import io.muserver.Method;
import io.muserver.MuServerBuilder;
import io.muserver.handlers.ResourceHandlerBuilder;
import io.muserver.rest.RestHandlerBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

import static io.muserver.ContextHandlerBuilder.context;

@Component
public class MuServer {
    public static SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-M-dd hh:mm:ss", Locale.ENGLISH);

    @Autowired
    private ReservationRepository repository;

    @PostConstruct
    public void init() throws Exception {

        ReservationController reservationController = new ReservationController(repository);

        io.muserver.MuServer server = MuServerBuilder
                .httpServer()
                .withHttpsPort(8081)
                .addHandler(context("/")
                        .addHandler(ResourceHandlerBuilder.classpathHandler("/")))
                .addHandler(Method.GET, "/", (request, response, pathParams) -> response.write("Hello, world"))
                .addHandler(RestHandlerBuilder.restHandler(reservationController)
                        .withOpenApiJsonUrl("/openapi.json")
                        .withOpenApiHtmlUrl("/api.html")
                        .addCustomWriter(new JacksonJaxbJsonProvider())
                        .addCustomReader(new JacksonJaxbJsonProvider()))
                .start();

		System.out.println("Started server at "+server.uri());
    }
}
