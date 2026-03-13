package org.example.backendadventureapp.controller;

import org.example.backendadventureapp.model.Reservation;
import org.example.backendadventureapp.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//Note: Default behavior of Spring when using @RestController:
//    * Wraps RunTimeExceptions in HTTP 500 response.

@RestController
@CrossOrigin(origins = "http://localhost:63342")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;


    //Fetch single Reservation object on bookingNumber attribute:
    //Note: If reservation is null, and an exception is thrown, this endpoints might return an http response without a body.
    @GetMapping("/{bookingNumber}")
    public Reservation getReservationByBookingNumber(@PathVariable String bookingNumber) {
        Reservation reservation = reservationService.findReservationByBookingnumber(bookingNumber);
        if(reservation == null) {
            throw new RuntimeException("No reservation was found for the Bookingnumber: " + bookingNumber);
        }
        return reservation;
    }

    @PostMapping("/reservation")
    public Reservation bookReservation(@RequestBody Reservation reservation){
        return reservationService.createReservation(reservation);
    }

    //Cancel single reservation object on object:(no deletion of reservation entity, but update of its attributes,
    //so that Timeslot objects related to Reservation object are unrelated again.
    @PostMapping("reservation/{id}/cancel")
    public void deleteReservationById(@PathVariable Integer id) {
        reservationService.cancelReservation(id);
    }

}
