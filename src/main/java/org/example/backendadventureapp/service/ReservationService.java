package org.example.backendadventureapp.service;

import org.example.backendadventureapp.model.*;
import org.example.backendadventureapp.model.Package;
import org.example.backendadventureapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

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
    private PackageRepository packageRepository;
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

        System.out.println("firstName: " + customer.getFirstName());
        System.out.println("lastName: " + customer.getLastName());
        System.out.println("email: " + customer.getEmail());

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

            //null price = en firmapakke. Firmapakker er ikke begrænset i antal da de booker mange timeslots.
            if(reservation.getPrice() == null && t.getParticipants() > timeslot.getActivity().getMaxParticipants()){
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

    //opretter reservation for firmapakke
    public Reservation createPackageReservation(Integer packageId, String dayOfActivity, Integer participants, Customer customer){

        Package pkg = packageRepository.findById(packageId).orElseThrow();

        Reservation reservation = new Reservation();

        reservation.setCustomer(customer);
        reservation.setBookingNumber(generateBookingNumber());
        reservation.setDateOfReservation(LocalDateTime.now());

        reservation.setPrice(pkg.getPrice());

        List<Timeslot> selectedTimeslots = findPackageTimeslots(pkg, dayOfActivity, participants);

        reservation.setTimeslots(selectedTimeslots);

        validateCustomer(reservation);

        Customer savedCustomer = customerRepository.save(customer);
        reservation.setCustomer(savedCustomer);

        attachReservationToTimeslots(reservation);

        return reservationRepository.save(reservation);
    }

    // Find konkrete timeslots til firmapakken (2 timeslots per aktivitet)
    public List<Timeslot> findPackageTimeslots(Package pkg, String dayOfActivity, Integer participants){

        List<List<Timeslot>> activityTimeslots = new ArrayList<>();

        for(Activity activity : pkg.getActivities()){

            List<Timeslot> timeslots = timeslotRepository.findAllByActivityId(activity.getId());

            List<Timeslot> valid = new ArrayList<>();

            for(Timeslot t : timeslots){
                if(t.getReservation() == null &&
                t.getDayOfActivity().toString().equals(dayOfActivity)){
                    t.setParticipants(participants);
                    valid.add(t);

                    if(valid.size() == 2){
                        break;
                    }
                }
            }

            if(valid.size() < 2){
                return new ArrayList<>();
            }

            activityTimeslots.add(valid);
        }

        List<Timeslot> result = new ArrayList<>();

        for(int round = 0; round < 2; round++){
            for(List<Timeslot> timeslots : activityTimeslots){
                result.add(timeslots.get(round));
            }
        }

        return result;
    }

    //Finder start og slut tidspunkt for en firmapakke
    public String getPackageTimeRange(List<Timeslot> timeslots){

        LocalDateTime start = timeslots.get(0).getStartTime();
        LocalDateTime end = timeslots.get(0).getEndTime();

        for(Timeslot t : timeslots){
            if(t.getStartTime().isBefore(start)){
                start = t.getStartTime();
            }

            if(t.getEndTime().isAfter(end)){
                end = t.getEndTime();
            }
        }

        return start.toLocalTime() + " - " + end.toLocalTime();
    }

    //wrapper-metode til frontend, der gør så man kan se timerange før man har booket
    public String getPackageTimeRangeForDate(Integer packageId, String dayOfActivity, Integer participants){

        Package pkg = packageRepository.findById(packageId).orElseThrow();

        List<Timeslot> timeslots =
                findPackageTimeslots(pkg, dayOfActivity, participants);

        if(timeslots.isEmpty()){
            throw new RuntimeException("Ingen tider tilgængelige");
        }

        return getPackageTimeRange(timeslots);
    }

    //Finder timeslots til kalenderen for firmapakker
    public List<Timeslot> getPackageTimeslots(Integer packageId){

        Package pkg = packageRepository.findById(packageId).orElseThrow();

        List<Timeslot> result = new ArrayList<>();

        for(Activity activity : pkg.getActivities()){
            result.addAll(timeslotRepository.findAllByActivityId(activity.getId()));
        }

        return result;
    }

    //wrapper-metode der gør at frontend kan se hvilke dage der er ledige før man har klikket på datoen
    public List<String> getAvailablePackageDays(Integer packageId, Integer participants){

        Package pkg = packageRepository.findById(packageId).orElseThrow();

        List<String> availableDays = new ArrayList<>();

        List<Timeslot> allTimeslots = getPackageTimeslots(packageId);

        for(Timeslot t : allTimeslots){
            String day = t.getDayOfActivity().toString();

            if(!availableDays.contains(day)){

                List<Timeslot> timeslots = findPackageTimeslots(pkg, day, participants);

                    if(!timeslots.isEmpty()){
                        availableDays.add(day);
                    }
                }
            }

        return availableDays;
    }
}
