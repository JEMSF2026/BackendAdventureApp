package org.example.backendadventureapp.controller;

import org.example.backendadventureapp.model.Reservation;
import org.example.backendadventureapp.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;


    //Fetch single Reservation object on bookingNumber attribute:
    @GetMapping("/{bookingNumber}")
    public Reservation getReservationByBookingNumber(@PathVariable String bookingNumber) {
        Reservation reservation = reservationService.findReservationByBookingnumber(bookingNumber);
        if(reservationService == null) {
            throw new RuntimeException("No reservation was found for the Bookingnumber: " + bookingNumber);
        }
        return reservation;
    }
}
