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
}
