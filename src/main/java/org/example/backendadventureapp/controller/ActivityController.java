package org.example.backendadventureapp.controller;

import org.example.backendadventureapp.model.Activity;
import org.example.backendadventureapp.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@CrossOrigin(origins = "http://localhost:63342")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/activities")
    public List<Activity> getAllActivities() {
        return activityService.getAllActivities();
    }
}
