package org.example.backendadventureapp.controller;

import org.example.backendadventureapp.model.Reservation;
import org.example.backendadventureapp.service.ReservationService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ReservationService reservationService;


    @Test
    void getReservationByBookingNumber_returnsReservation() throws Exception {

        // Arrange
        String bookingNumber = "ABC123";

        Reservation reservation = new Reservation();
        reservation.setBookingNumber(bookingNumber);

        when(reservationService.findReservationByBookingnumber(bookingNumber))
                .thenReturn(reservation);

        // Act + Assert
        mockMvc.perform(get("/reservation/{bookingNumber}", bookingNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingNumber").value("ABC123"));
    }

    @Test
    void bookReservation_returns200() throws Exception {

        Reservation reservation = new Reservation();

        when(reservationService.createReservation(any()))
                .thenReturn(reservation);

        mockMvc.perform(post("/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void getPackageTimeRange_returnsTimeRange() throws Exception {

        String range = "09:00 - 16:00";

        when(reservationService.getPackageTimeRangeForDate(1, "2026-03-20", 4))
                .thenReturn(range);

        mockMvc.perform(get("/packageTimeRange")
                        .param("packageId", "1")
                        .param("dayOfActivity", "2026-03-20")
                        .param("participants", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("09:00 - 16:00"));
    }

    @Test
    void bookPackageReservation_returnsReservation() throws Exception {

        Reservation reservation = new Reservation();
        reservation.setBookingNumber("87654321");

        when(reservationService.createPackageReservation(
                Mockito.eq(1),
                Mockito.eq("2026-03-20"),
                Mockito.eq(4),
                any()))
                .thenReturn(reservation);

        String customerJson = """
        {
            "firstName": "Test",
            "lastName": "Testensen",
            "email": "test@mail.dk",
            "phone": "12345678"
        }
        """;

        mockMvc.perform(post("/packageReservation")
                        .param("packageId", "1")
                        .param("dayOfActivity", "2026-03-20")
                        .param("participants", "4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingNumber").value("87654321"));
    }

    @Test
    void getPackageTimeslots_returnsList() throws Exception {

        when(reservationService.getPackageTimeslots(1))
                .thenReturn(List.of());

        mockMvc.perform(get("/packageTimeslots")
                        .param("packageId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAvailableDays_returnsDays() throws Exception {

        when(reservationService.getAvailablePackageDays(1, 4))
                .thenReturn(List.of("2026-03-20", "2026-03-21"));

        mockMvc.perform(get("/packageAvailableDays")
                        .param("packageId", "1")
                        .param("participants", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("2026-03-20"));
    }
}

