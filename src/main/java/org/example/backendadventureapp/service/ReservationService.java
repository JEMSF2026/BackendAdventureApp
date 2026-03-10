package org.example.backendadventureapp.service;

import org.example.backendadventureapp.model.Reservation;
import org.example.backendadventureapp.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    //fetch single reservation by bookingNumber:
    public Reservation findReservationByBookingnumber(String bookingNumber) {
        return reservationRepository.findByBookingNumber(bookingNumber);
    }
}
