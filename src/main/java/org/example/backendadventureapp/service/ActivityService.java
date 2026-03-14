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

    public Activity getActivityById(int id) {
        return activityRepository.findById(id).orElseThrow();
    }

    public Activity updateActivity(Activity activity) {
        Activity existing = activityRepository.findById(activity.getId()).orElseThrow();

        existing.setName(activity.getName());
        existing.setDescription(activity.getDescription());
        existing.setPrice(activity.getPrice());
        existing.setMinimumAge(activity.getMinimumAge());
        existing.setDurationMinutes(activity.getDurationMinutes());
        existing.setMaxParticipants(activity.getMaxParticipants());

        return activityRepository.save(existing);
    }
}
