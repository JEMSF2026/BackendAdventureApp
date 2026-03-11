package org.example.backendadventureapp.service;

import org.example.backendadventureapp.model.Reservation;
import org.example.backendadventureapp.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//Note:
//The service layer logic is tested in isolation: No Spring Context, no database.

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    //Mocking of repository class
    @Mock
    private ReservationRepository reservationRepository;

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
}