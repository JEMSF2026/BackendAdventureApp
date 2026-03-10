package org.example.backendadventureapp.repository;

import org.example.backendadventureapp.model.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {
    List<Timeslot> findAllByActivityId(int activityId);
}
