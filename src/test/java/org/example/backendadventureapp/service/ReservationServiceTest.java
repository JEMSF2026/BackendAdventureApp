package org.example.backendadventureapp.service;

import org.example.backendadventureapp.model.*;
import org.example.backendadventureapp.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//Note:
//The service layer logic is tested in isolation: No Spring Context, no database.

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    //Mocking of repository class
    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerTypeRepository customerTypeRepository;

    @Mock
    private TimeslotRepository timeslotRepository;

    @Mock
    private ActivityRepository activityRepository;

    //Service is injected
    @InjectMocks
    private ReservationService reservationService;

    @Test
    void findReservationByBookingnumber_returnsReservation() {

        // Arrange values
        String bookingNumber = "ABC123";


        Reservation reservation = new Reservation();
        reservation.setBookingNumber(bookingNumber);

        //actions of the repository layer is mocked, so that there is always consistent behavior of this layer.
        when(reservationRepository.findByBookingNumber(bookingNumber))
                .thenReturn(reservation);

        //the result/returned value of the service method call is store in result. the actual call to service method is performed here.
        Reservation result = reservationService.findReservationByBookingnumber(bookingNumber);

        // Asserts that the correct value is returned from exactly the service method
        assertEquals("ABC123", result.getBookingNumber());

        verify(reservationRepository, times(1))
                .findByBookingNumber(bookingNumber);
    }

    @Test
    void calculatePrice_setsCorrectPrice(){

        Activity activity = new Activity();
        activity.setPrice(200.0);

        Timeslot t1 = new Timeslot();
        t1.setActivity(activity);

        Timeslot t2 = new Timeslot();
        t2.setActivity(activity);

        Reservation reservation = new Reservation();
        reservation.setTimeslots(List.of(t1, t2));

        reservationService.calculatePrice(reservation);

        assertEquals(400, reservation.getPrice());
    }

    @Test
    void validateCustomer_throwsException_whenCustomerInfoMissing(){

        Reservation reservation = new Reservation();

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> reservationService.createReservation(reservation)
        );

        assertEquals("Kundeoplysninger er påkrævet", exception.getMessage());
    }

    @Test
    void attachReservationToTimeslots_setReservation(){

        Timeslot dbTimeslot = new Timeslot();
        dbTimeslot.setId(1);
        dbTimeslot.setReservation(null);

        when(timeslotRepository.findById(1))
                .thenReturn(Optional.of(dbTimeslot));

        Timeslot requestTimeslot = new Timeslot();
        requestTimeslot.setId(1);
        requestTimeslot.setParticipants(5);

        Reservation reservation = new Reservation();
        reservation.setTimeslots(List.of(requestTimeslot));

        reservationService.attachReservationToTimeslots(reservation);

        assertEquals(reservation, dbTimeslot.getReservation());
        assertEquals(5, dbTimeslot.getParticipants());
    }

    @Test
    void createReservation_savesReservation(){

        CustomerType type = new CustomerType();
        type.setId(1);

        Customer customer = new Customer();
        customer.setFirstName("Test");
        customer.setLastName("Testensen");
        customer.setEmail("test@test.dk");
        customer.setCustomerType(type);

        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setTimeslots(new ArrayList<>());

        when(customerTypeRepository.findById(1))
                .thenReturn(Optional.of(type));

        when(customerRepository.save(any()))
                .thenReturn(customer);

        when(reservationRepository.save(any()))
                .thenReturn(reservation);

        Reservation result = reservationService.createReservation(reservation);

        assertNotNull(result);

    }
}