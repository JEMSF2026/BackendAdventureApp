package org.example.backendadventureapp.service;

import org.example.backendadventureapp.model.Timeslot;
import org.example.backendadventureapp.repository.TimeslotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeslotService {

    @Autowired
    private TimeslotRepository timeslotRepository;

    public List<Timeslot> getAllTimeslotsByActivityId(int activityId) {
        return timeslotRepository.findAllByActivityId(activityId);
    }

    public Timeslot createTimeslot(Timeslot timeslot) {
        return timeslotRepository.save(timeslot);
    }

    public void deleteTimeslot(int timeslotId) {
        Timeslot timeslot = timeslotRepository.findById(timeslotId).orElseThrow();
        if (timeslot.getReservation() == null) {
            timeslotRepository.deleteById(timeslotId);
        } else {
            throw new RuntimeException("Timeslottet kan ikke slettes fordi der er en reservation på den");
        }
    }
}
