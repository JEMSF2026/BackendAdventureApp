package org.example.backendadventureapp.service;

import org.example.backendadventureapp.model.Activity;
import org.example.backendadventureapp.model.Reservation;
import org.example.backendadventureapp.model.Timeslot;
import org.example.backendadventureapp.repository.ActivityRepository;
import org.example.backendadventureapp.repository.EquipmentRepository;
import org.example.backendadventureapp.repository.PackageRepository;
import org.example.backendadventureapp.repository.TimeslotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityServiceTest {

    @Mock
    ActivityRepository activityRepository;

    @Mock
    TimeslotRepository timeslotRepository;

    @Mock
    EquipmentRepository equipmentRepository;

    @Mock
    PackageRepository packageRepository;

    @InjectMocks
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

    @Test
    void getActivityById_returnsActivity() {
        Activity activity = new Activity(1, "Paintball", "Beskrivelse", 249.00, 12, 90, 20, Collections.emptyList());
        when(activityRepository.findById(1)).thenReturn(Optional.of(activity));

        Activity result = activityService.getActivityById(1);

        assertEquals(1, result.getId());
        assertEquals("Paintball", result.getName());
        verify(activityRepository, times(1)).findById(1);
    }

    @Test
    void deleteActivityById_callsRepositoryDelete() {
        doNothing().when(activityRepository).deleteById(1);
        when(timeslotRepository.findAllByActivityId(1)).thenReturn(Collections.emptyList());
        when(equipmentRepository.findAllByActivityId(1)).thenReturn(Collections.emptyList());
        when(packageRepository.findAll()).thenReturn(Collections.emptyList());

        activityService.deleteActivityById(1);

        verify(activityRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteActivityById_throwsWhenActivityHasReservation() {
        Reservation reservation = new Reservation(1, null, "12345678", null, null, null);
        Timeslot timeslot = new Timeslot(1, null, reservation, null, null, null, null, 0);
        when(timeslotRepository.findAllByActivityId(1)).thenReturn(List.of(timeslot));

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> activityService.deleteActivityById(1));

        assertTrue(ex.getMessage().contains("12345678"));
        verify(activityRepository, never()).deleteById(1);
    }

    @Test
    void updateActivity_returnsUpdatedActivity() {
        Activity existing = new Activity(1, "Paintball", "Gammel beskrivelse", 249.00, 12, 90, 20, Collections.emptyList());
        Activity updated = new Activity(1, "Paintball Pro", "Ny beskrivelse", 299.00, 14, 90, 20, Collections.emptyList());
        when(activityRepository.findById(1)).thenReturn(Optional.of(existing));
        when(activityRepository.save(existing)).thenReturn(updated);

        Activity result = activityService.updateActivity(updated);

        assertEquals("Paintball Pro", result.getName());
        assertEquals(299.00, result.getPrice());
        verify(activityRepository, times(1)).findById(1);
        verify(activityRepository, times(1)).save(existing);
    }
}
