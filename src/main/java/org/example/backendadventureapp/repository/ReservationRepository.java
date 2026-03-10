package org.example.backendadventureapp.repository;

import org.example.backendadventureapp.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    Reservation findByBookingNumber(String bookingNumber);
}
