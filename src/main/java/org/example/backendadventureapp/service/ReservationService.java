package org.example.backendadventureapp.service;

import org.example.backendadventureapp.model.*;
import org.example.backendadventureapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private CustomerTypeRepository customerTypeRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TimeslotRepository timeslotRepository;

    //fetch single reservation by bookingNumber:
    public Reservation findReservationByBookingnumber(String bookingNumber) {
        return reservationRepository.findByBookingNumber(bookingNumber);
    }

    public Reservation createReservation(Reservation reservation) {

        validateCustomer(reservation);

        Customer customer = reservation.getCustomer();
        customer = customerRepository.save(customer);

        reservation.setCustomer(customer);
        reservation.setBookingNumber(generateBookingNumber());
        reservation.setDateOfReservation(LocalDateTime.now());

        attachReservationToTimeslots(reservation);

        calculatePrice(reservation);

        return reservationRepository.save(reservation);
    }

    private void validateCustomer(Reservation reservation){

        if(reservation.getCustomer() == null){
            throw new RuntimeException("Kundeoplysninger er påkrævet");
        }

        Customer customer = reservation.getCustomer();

        if(reservation.getCustomer().getFirstName() == null ||
                reservation.getCustomer().getLastName() == null ||
                reservation.getCustomer().getEmail() == null){
            throw new RuntimeException("Manglende kundeoplsyninger");
        }

        CustomerType type = customerTypeRepository
                .findById(customer.getCustomerType().getId())
                .orElseThrow();

        if(type.getId() == 2){
            if(customer.getCompanyName() == null || customer.getCompanyName().isBlank() ||
                    customer.getCvr() == null){
                throw new RuntimeException("CVR og Virksomhedsnavn påkrævet for virksomheder");
            }
        }

        customer.setCustomerType(type);
    }

    public void attachReservationToTimeslots(Reservation reservation) {

        List<Timeslot> attachedTimeslots = new ArrayList<>();

        for (Timeslot t : reservation.getTimeslots()){
            Timeslot timeslot = timeslotRepository.findById(t.getId()).orElseThrow();

            if(timeslot.getReservation() != null){
                throw new RuntimeException("Tidspunktet er booket af en anden kunde.");
            }

            if(t.getParticipants() > timeslot.getActivity().getMaxParticipants()){
                throw new RuntimeException("For mange deltagere til aktiviteten");
            }

            timeslot.setReservation(reservation);
            timeslot.setParticipants(t.getParticipants());

            attachedTimeslots.add(timeslot);
        }

        reservation.setTimeslots(attachedTimeslots);
    }

    public void calculatePrice(Reservation reservation){

        double totalPrice = 0;

        for(var timeslot : reservation.getTimeslots()){
            totalPrice += timeslot.getActivity().getPrice();
        }

        reservation.setPrice(totalPrice);
    }

    public String generateBookingNumber(){

        Random random = new Random();
        int number = random.nextInt(90000000) + 10000000;

        return String.valueOf(number);
    }
}
