package org.example.backendadventureapp.controller;

import org.example.backendadventureapp.model.Activity;
import org.example.backendadventureapp.service.ActivityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ActivityController.class)
class ActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ActivityService activityService;

    @Test
    void getActivities_returnsListOfActivities() throws Exception {
        List<Activity> activities = List.of(
                new Activity(1, "Paintball", "Beskrivelse", 249.00, 12, 90, 20, Collections.emptyList()),
                new Activity(2, "Go-kart", "Beskrivelse", 199.00, 10, 30, 12, Collections.emptyList())
        );
        when(activityService.getAllActivities()).thenReturn(activities);

        mockMvc.perform(get("/activities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Paintball"))
                .andExpect(jsonPath("$[1].name").value("Go-kart"));

        verify(activityService, times(1)).getAllActivities();
    }

    @Test
    void getActivities_returnsEmptyListWhenNoActivities() throws Exception {
        when(activityService.getAllActivities()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/activities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(activityService, times(1)).getAllActivities();
    }

    @Test
    void getActivities_returnsSingleActivity() throws Exception {
        Activity activity = new Activity(1, "Klatrepark", "Beskrivelse", 189.00, 7, 90, 25, Collections.emptyList());
        when(activityService.getAllActivities()).thenReturn(List.of(activity));

        mockMvc.perform(get("/activities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Klatrepark"))
                .andExpect(jsonPath("$[0].price").value(189.00))
                .andExpect(jsonPath("$[0].minimumAge").value(7));

        verify(activityService, times(1)).getAllActivities();
    }

    @Test
    void createActivity_returns201WithCreatedActivity() throws Exception {
        Activity created = new Activity(1, "Klatrepark", "Beskrivelse", 189.00, 7, 90, 25, Collections.emptyList());
        when(activityService.createActivity(any(Activity.class))).thenReturn(created);

        mockMvc.perform(post("/activities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Klatrepark\",\"description\":\"Beskrivelse\",\"price\":189.00,\"minimumAge\":7,\"durationMinutes\":90,\"maxParticipants\":25}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Klatrepark"));

        verify(activityService, times(1)).createActivity(any(Activity.class));
    }

    @Test
    void getActivityById_returnsActivity() throws Exception {
        Activity activity = new Activity(1, "Paintball", "Beskrivelse", 249.00, 12, 90, 20, Collections.emptyList());
        when(activityService.getActivityById(1)).thenReturn(activity);

        mockMvc.perform(get("/activities/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Paintball"));

        verify(activityService, times(1)).getActivityById(1);
    }

    @Test
    void updateActivity_returnsUpdatedActivity() throws Exception {
        Activity updated = new Activity(1, "Paintball Pro", "Ny beskrivelse", 299.00, 14, 90, 20, Collections.emptyList());
        when(activityService.updateActivity(any(Activity.class))).thenReturn(updated);

        mockMvc.perform(put("/activities/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Paintball Pro\",\"description\":\"Ny beskrivelse\",\"price\":299.00,\"minimumAge\":14,\"durationMinutes\":90,\"maxParticipants\":20}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Paintball Pro"))
                .andExpect(jsonPath("$.price").value(299.00));

        verify(activityService, times(1)).updateActivity(any(Activity.class));
    }

    @Test
    void deleteActivity_returns200() throws Exception {
        doNothing().when(activityService).deleteActivityById(1);

        mockMvc.perform(delete("/activities/delete/1"))
                .andExpect(status().isOk());

        verify(activityService, times(1)).deleteActivityById(1);
    }

    @Test
    void deleteActivity_returns409WhenActivityHasReservation() throws Exception {
        doThrow(new IllegalStateException("Aktiviteten kan ikke slettes, da bookingnummer 12345678 har booket aktiviteten."))
                .when(activityService).deleteActivityById(1);

        mockMvc.perform(delete("/activities/delete/1"))
                .andExpect(status().isConflict())
                .andExpect(content().string("Aktiviteten kan ikke slettes, da bookingnummer 12345678 har booket aktiviteten."));

        verify(activityService, times(1)).deleteActivityById(1);
    }
}