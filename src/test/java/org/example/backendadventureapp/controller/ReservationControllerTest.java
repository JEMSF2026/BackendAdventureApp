package org.example.backendadventureapp.controller;

import org.example.backendadventureapp.model.Reservation;
import org.example.backendadventureapp.service.ReservationService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

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
        mockMvc.perform(get("/reservations/{bookingNumber}", bookingNumber))
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
}

