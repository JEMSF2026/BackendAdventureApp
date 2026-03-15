package org.example.backendadventureapp.controller;

import org.example.backendadventureapp.model.Customer;
import org.example.backendadventureapp.model.Reservation;
import org.example.backendadventureapp.model.Timeslot;
import org.example.backendadventureapp.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Note: Default behavior of Spring when using @RestController:
//    * Wraps RunTimeExceptions in HTTP 500 response.

@RestController
@CrossOrigin(origins = {"http://localhost:63342", "http://4.235.123.111"})
public class ReservationController {

    @Autowired
    private ReservationService reservationService;


    //Fetch single Reservation object on bookingNumber attribute:
    //Note: If reservation is null, and an exception is thrown, this endpoints might return an http response without a body.
    @GetMapping("/reservation/{bookingNumber}")
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

    @GetMapping("/packageTimeRange")
    public String getPackageTimeRange(@RequestParam Integer packageId, @RequestParam String dayOfActivity, @RequestParam
                                      Integer participants){

        return reservationService.getPackageTimeRangeForDate(packageId, dayOfActivity, participants);
    }

    @PostMapping("/packageReservation")
    public Reservation bookPackageReservation(
            @RequestParam Integer packageId,
            @RequestParam String dayOfActivity,
            @RequestParam Integer participants,
            @RequestBody Customer customer
            ){
        return reservationService.createPackageReservation(packageId, dayOfActivity, participants, customer);
    }

    @GetMapping("/packageTimeslots")
    public List<Timeslot> getPackageTimeslots(@RequestParam Integer packageId){
        return reservationService.getPackageTimeslots(packageId);
    }

    @GetMapping("/packageAvailableDays")
    public List<String> getAvailableDays(@RequestParam Integer packageId, @RequestParam Integer participants){
        return reservationService.getAvailablePackageDays(packageId, participants);
    }
    //Cancel single reservation object on object:(no deletion of reservation entity, but update of its attributes,
    //so that Timeslot objects related to Reservation object are unrelated again.
    @PostMapping("reservation/{id}/cancel")
    public void deleteReservationById(@PathVariable Integer id) {
        reservationService.cancelReservation(id);
    }

}
