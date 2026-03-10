package org.example.backendadventureapp.model;

import org.example.backendadventureapp.repository.TimeslotRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TimeslotTest {

    @Autowired
    TimeslotRepository timeslotRepository;

    @Test
    void test() {
        List<Timeslot> timeslots = timeslotRepository.findAllByActivityId(1);
        assertEquals(4,timeslots.size());
    }

}