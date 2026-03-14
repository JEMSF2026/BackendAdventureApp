package org.example.backendadventureapp.controller;

import org.example.backendadventureapp.model.Timeslot;
import org.example.backendadventureapp.repository.TimeslotRepository;
import org.example.backendadventureapp.service.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:63342",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT}
)
public class TimeslotController {

    @Autowired
    private TimeslotService timeslotService;

    @GetMapping("/timeslots/{activityId}")
    public List<Timeslot> getAllTimeslotsByActivityId(@PathVariable int activityId) {
        return timeslotService.getAllTimeslotsByActivityId(activityId);
    }

    @PostMapping("/timeslots")
    public ResponseEntity<Timeslot> createTimeslot(@RequestBody Timeslot timeslot) {
        Timeslot created = timeslotService.createTimeslot(timeslot);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/timeslot/delete/{timeslotId}")
    public void deleteTimeslot(@PathVariable int timeslotId) {
        timeslotService.deleteTimeslot(timeslotId);
    }
}
