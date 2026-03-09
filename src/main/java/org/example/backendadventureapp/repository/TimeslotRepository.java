package org.example.backendadventureapp.repository;

import org.example.backendadventureapp.model.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeslotRepository extends JpaRepository<Timeslot, Integer> {
}
