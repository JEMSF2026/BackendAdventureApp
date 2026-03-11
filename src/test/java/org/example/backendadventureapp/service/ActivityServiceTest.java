package org.example.backendadventureapp.service;

import org.example.backendadventureapp.model.Activity;
import org.example.backendadventureapp.repository.ActivityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ActivityServiceTest {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    ActivityService activityService;

    @Test
    void getAllActivities_returnsListFromRepository() {
        Activity activity = new Activity(1, "Paintball", "Beskrivelse", 249.00, 12, 90, 20, Collections.emptyList());
        when(activityRepository.findAll()).thenReturn(List.of(activity));

        List<Activity> result = activityService.getAllActivities();

        assertEquals(1, result.size());
        assertEquals("Paintball", result.get(0).getName());
        verify(activityRepository, times(1)).findAll();
    }

    @Test
    void getAllActivities_returnsEmptyListWhenNoActivities() {
        when(activityRepository.findAll()).thenReturn(Collections.emptyList());

        List<Activity> result = activityService.getAllActivities();

        assertTrue(result.isEmpty());
        verify(activityRepository, times(1)).findAll();
    }

    @Test
    void createActivity_returnsSavedActivity() {
        Activity activity = new Activity(0, "Klatrepark", "Beskrivelse", 189.00, 7, 90, 25, Collections.emptyList());
        Activity saved = new Activity(1, "Klatrepark", "Beskrivelse", 189.00, 7, 90, 25, Collections.emptyList());
        when(activityRepository.save(activity)).thenReturn(saved);

        Activity result = activityService.createActivity(activity);

        assertEquals(1, result.getId());
        assertEquals("Klatrepark", result.getName());
        verify(activityRepository, times(1)).save(activity);
    }

    @Test
    void getAllActivities_returnsAllActivities() {
        List<Activity> activities = List.of(
                new Activity(1, "Paintball", "Beskrivelse", 249.00, 12, 90, 20, Collections.emptyList()),
                new Activity(2, "Go-kart", "Beskrivelse", 199.00, 10, 30, 12, Collections.emptyList()),
                new Activity(3, "Klatrepark", "Beskrivelse", 189.00, 7, 90, 25, Collections.emptyList())
        );
        when(activityRepository.findAll()).thenReturn(activities);

        List<Activity> result = activityService.getAllActivities();

        assertEquals(3, result.size());
        verify(activityRepository, times(1)).findAll();
    }
}