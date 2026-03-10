package org.example.backendadventureapp.controller;

import org.example.backendadventureapp.model.Timeslot;
import org.example.backendadventureapp.service.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
public class TimeslotController {

    @Autowired
    TimeslotService timeslotService;

    @GetMapping("/timeslots/{activityId}")
    public List<Timeslot> getAllTimeslotsByActivityId(@PathVariable int activityId) {
        return timeslotService.getAllTimeslotsByActivityId(activityId);
    }

}
