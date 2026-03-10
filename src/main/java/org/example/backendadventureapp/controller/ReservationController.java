package org.example.backendadventureapp.controller;

import org.example.backendadventureapp.model.Reservation;
import org.example.backendadventureapp.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService rerservationService) {
        this.reservationService = rerservationService;
    }
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
