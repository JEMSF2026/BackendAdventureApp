package org.example.backendadventureapp.service;

import org.example.backendadventureapp.model.Activity;
import org.example.backendadventureapp.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public List<Activity> getAllActivities(){
        return activityRepository.findAll();
    }

    public Activity createActivity(Activity activity) {
        // save() er en indbygget JPA-metode der både opretter og opdaterer
        return activityRepository.save(activity);
    }
}
