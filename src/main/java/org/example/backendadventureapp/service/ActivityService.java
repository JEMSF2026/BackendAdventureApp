package org.example.backendadventureapp.service;


import org.example.backendadventureapp.model.Activity;
import org.example.backendadventureapp.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    @Autowired
    ActivityRepository activityRepository;


    public List<Activity> getAllActivities(){
        return activityRepository.findAll();
    }


}
