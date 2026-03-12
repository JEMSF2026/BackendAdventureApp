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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}