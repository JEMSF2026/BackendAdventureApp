package org.example.backendadventureapp.service;

import org.example.backendadventureapp.model.Customer;
import org.example.backendadventureapp.model.Reservation;
import org.example.backendadventureapp.repository.CustomerRepository;
import org.example.backendadventureapp.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              CustomerRepository customerRepository){

        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
    }

    public Reservation createReservation(Reservation reservation){
        reservation.setBookingNumber(generateBookingNumber());
        reservation.setDateOfReservation(LocalDateTime.now());

    //fetch single reservation by bookingNumber:
    public Reservation findReservationByBookingnumber(String bookingNumber) {
        return reservationRepository.findByBookingNumber(bookingNumber);
        Customer savedCustomer = customerRepository.save(reservation.getCustomer());
        reservation.setCustomer(savedCustomer);

        return reservationRepository.save(reservation);
    }

    public String generateBookingNumber(){

        Random random = new Random();
        int number = random.nextInt(90000000) + 10000000;

        return String.valueOf(number);
    }
}
