package com.mzhang.restaurant.controller;

import com.mzhang.restaurant.model.BookRequest;
import com.mzhang.restaurant.model.Reservation;
import com.mzhang.restaurant.repository.ReservationRepository;
import io.muserver.rest.ApiResponse;
import io.muserver.rest.Description;
import org.springframework.util.ObjectUtils;

import javax.ws.rs.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Path("/reservations")
public class ReservationController {
    public final static SimpleDateFormat dayOnlyFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    public final static int maxReservationHours = 2;

    private final ReservationRepository repository;

    public ReservationController(ReservationRepository repository) {
        this.repository = repository;
    }

    /**
     * This method is used to look up all reservations by day
     * @param day - String like "yyyy-mm-dd"
     * @return - all reservations as a list
     */
    @GET
    @Path("/{day}")
    @Produces("application/json")
    @Description("Lookup reservations by the day")
    @ApiResponse(code = "200", message = "Success")
    @ApiResponse(code = "404", message = "No reservations with that day found")
    public List<Reservation> lookupByDay(@Description("The day of reservation")@PathParam("day") String day) throws Exception {
        List<Reservation> reservationsByDay = repository.lookupReservationsByDay(day);

        if (ObjectUtils.isEmpty(reservationsByDay)) {
            throw new NotFoundException("No reservations for the day - " + day);
        }

        return reservationsByDay;
    }

    /**
     * This method is used to book a reservation, will save through repository
     * @param request - to see BookRequest class
     * @return - instance of reservation
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Description("Book a reservation")
    @ApiResponse(code = "201", message = "The reservation was booked")
    @ApiResponse(code = "400", message = "The request was invalid")
    public Reservation book(@Description("Json Request like: {\"customerName\": \"xxx\", \"tableSize\": 2, \"reservationDateTime\": \"2024-03-01\"}") BookRequest request) {
        if (request.getReservationHours() > maxReservationHours)
            request.setReservationHours(maxReservationHours);

        // Here should include some validation logic to check reservation can be applied or not

        Reservation reservation = Reservation
                .builder()
                .customerName(request.getCustomerName())
                .tableSize(request.getTableSize())
                .reservationDateTime(request.getReservationDateTime())
                .bookDateTime(new Date())
                .reservationHours(request.getReservationHours())
                .build();

        repository.save(reservation);

        return reservation;
    }

}
