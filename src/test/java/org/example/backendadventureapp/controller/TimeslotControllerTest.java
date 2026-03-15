package org.example.backendadventureapp.controller;

import org.example.backendadventureapp.model.Timeslot;
import org.example.backendadventureapp.service.TimeslotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(TimeslotController.class)
class TimeslotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TimeslotService timeslotService;

    @Test
    void createTimeslot_returns201WithCreatedTimeslot() throws Exception {
        Timeslot created = new Timeslot();
        created.setId(1);
        created.setDayOfActivity(LocalDate.of(2026, 3, 20));
        created.setStartTime(LocalDateTime.of(2026, 3, 20, 10, 0));
        created.setEndTime(LocalDateTime.of(2026, 3, 20, 11, 0));
        created.setParticipants(10);

        when(timeslotService.createTimeslot(any(Timeslot.class))).thenReturn(created);

        mockMvc.perform(post("/timeslots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dayOfActivity\":\"2026-03-20\",\"startTime\":\"2026-03-20T10:00:00\",\"endTime\":\"2026-03-20T11:00:00\",\"participants\":10}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.dayOfActivity").value("2026-03-20"))
                .andExpect(jsonPath("$.startTime").value("2026-03-20T10:00:00"))
                .andExpect(jsonPath("$.endTime").value("2026-03-20T11:00:00"))
                .andExpect(jsonPath("$.participants").value(10));

        verify(timeslotService, times(1)).createTimeslot(any(Timeslot.class));
    }

    @Test
    void getAllTimeslotsByActivityId_shouldReturnAllTimeslotsByActivityId() throws Exception {

        int activityId = 1;

        Timeslot timeslot = new Timeslot();
        timeslot.setDayOfActivity(LocalDate.of(2026,3,7));
        timeslot.setStartTime(LocalDateTime.of(2026,3,7,13,30));
        timeslot.setEndTime(LocalDateTime.of(2026,3,7,14,30));


        when(timeslotService.getAllTimeslotsByActivityId(activityId)).thenReturn(List.of(timeslot));

        mockMvc.perform(get("/timeslots/{activityId}", activityId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].dayOfActivity").value("2026-03-07"))
                .andExpect(jsonPath("$[0].startTime").value("2026-03-07T13:30:00"))
                .andExpect(jsonPath("$[0].endTime").value("2026-03-07T14:30:00"));
    }

}