package org.example.backendadventureapp.service;

import org.example.backendadventureapp.model.*;
import org.example.backendadventureapp.model.Package;
import org.example.backendadventureapp.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Mock
    private PackageRepository packageRepository;

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
        reservation.setPrice(200.0);

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

    @Test
    void getPackageTimeRange_returnsCorrectRange(){

        Timeslot t1 = new Timeslot();
        t1.setStartTime(LocalDateTime.of(2026, 3, 20, 9, 0));
        t1.setEndTime(LocalDateTime.of(2026, 3, 20, 10, 0));

        Timeslot t2 = new Timeslot();
        t2.setStartTime(LocalDateTime.of(2026, 3, 20, 11, 0));
        t2.setEndTime(LocalDateTime.of(2026, 3, 20, 12, 0));

        List<Timeslot> timeslots = List.of(t1, t2);

        String result = reservationService.getPackageTimeRange(timeslots);

        assertEquals("09:00 - 12:00", result);
    }

    @Test
    void findPackageTimeslots_returnsTwoTimeslots_whenTwoAvailable(){

        Activity activity = new Activity();
        activity.setId(1);

        Package pkg = new Package();
        pkg.setActivities(List.of(activity));

        Timeslot t1 = new Timeslot();
        t1.setDayOfActivity(LocalDate.of(2026, 3, 20));
        t1.setReservation(null);

        Timeslot t2 = new Timeslot();
        t2.setDayOfActivity(LocalDate.of(2026, 3, 20));
        t2.setReservation(null);

        when(timeslotRepository.findAllByActivityId(1))
                .thenReturn(List.of(t1, t2));

        List<Timeslot> result = reservationService.findPackageTimeslots(pkg, "2026-03-20", 10);

        assertEquals(2, result.size());
    }

    @Test
    void findPackageTimeslots_returnsEmptyList_whenLessThanTwoTimeslots(){

        Activity activity = new Activity();
        activity.setId(1);

        Package pkg = new Package();
        pkg.setActivities(List.of(activity));

        Timeslot t1 = new Timeslot();
        t1.setDayOfActivity(LocalDate.of(2026, 3, 20));

        when(timeslotRepository.findAllByActivityId(1))
                .thenReturn(List.of(t1));

        List<Timeslot> result = reservationService.findPackageTimeslots(pkg, "2026-03-20", 10);

        assertTrue(result.isEmpty());
    }

    @Test
    void getAvailablePackageDays_returnsAvailableDay(){

        Activity activity = new Activity();
        activity.setId(1);

        Package pkg = new Package();
        pkg.setActivities(List.of(activity));

        Timeslot t1 = new Timeslot();
        t1.setDayOfActivity(LocalDate.of(2026, 3, 20));
        t1.setReservation(null);

        Timeslot t2 = new Timeslot();
        t2.setDayOfActivity(LocalDate.of(2026, 3, 20));
        t2.setReservation(null);

        when(packageRepository.findById(1))
                .thenReturn(Optional.of(pkg));

        when(timeslotRepository.findAllByActivityId(1))
                .thenReturn(List.of(t1, t2));

        List<String> result = reservationService.getAvailablePackageDays(1, 10);

        assertTrue(result.contains("2026-03-20"));
    }

    @Test
    void createPackageReservation_savesReservation(){

        Package pkg = new Package();
        pkg.setId(1);
        pkg.setPrice(500.0);
        pkg.setActivities(new ArrayList<>());

        CustomerType type = new CustomerType();
        type.setId(1);

        Customer customer = new Customer();
        customer.setFirstName("Test");
        customer.setLastName("Testsen");
        customer.setEmail("test@mail.dk");
        customer.setCustomerType(type);

        when(packageRepository.findById(1))
                .thenReturn(Optional.of(pkg));

        when(customerTypeRepository.findById(1))
                .thenReturn(Optional.of(type));

        when(customerRepository.save(any()))
                .thenReturn(customer);

        when(reservationRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Reservation result = reservationService.createPackageReservation(1, "2026", 10, customer);

        assertNotNull(result);
        assertEquals(500.0, result.getPrice());
    }
}