package org.example.backendadventureapp.controller;

import org.example.backendadventureapp.model.Activity;
import org.example.backendadventureapp.repository.ActivityRepository;
import org.example.backendadventureapp.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @GetMapping("/activities")
    public List<Activity> activities(){
        return activityService.getAllActivities();
    }


}
