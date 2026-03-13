package org.example.backendadventureapp.controller;

import org.example.backendadventureapp.model.Activity;
import org.example.backendadventureapp.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(
        origins = "http://localhost:63342",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}
)
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/activities")
    public List<Activity> getAllActivities() {
        return activityService.getAllActivities();
    }

    @GetMapping("/activities/{id}")
    public Activity getActivityById(@PathVariable int id) {
        return activityService.getActivityById(id);
    }

    // Modtager en aktivitet som JSON body og gemmer den i databasen
    // Returnerer 201 Created med den oprettede aktivitet (inkl. det auto-genererede id)
    @PostMapping("/activities")
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) {
        Activity created = activityService.createActivity(activity);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Modtager opdaterede aktivitetsoplysninger og erstatter de eksisterende
    // Returnerer den opdaterede aktivitet
    @PutMapping("/activities/update/{activityId}")
    public Activity updateActivity(@PathVariable int activityId, @RequestBody Activity activity) {
        activity.setId(activityId);
        return activityService.updateActivity(activity);
    }
}
