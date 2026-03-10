package org.example.backendadventureapp.repository;

import org.example.backendadventureapp.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
}
