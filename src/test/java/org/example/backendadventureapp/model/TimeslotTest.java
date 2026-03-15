package org.example.backendadventureapp.model;

import org.example.backendadventureapp.repository.ActivityRepository;
import org.example.backendadventureapp.repository.TimeslotRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TimeslotTest {

    @Autowired
    TimeslotRepository timeslotRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Test
    void findAllByActivityId_returnsFourTimeslots() {
        Activity activity = new Activity();
        activity = activityRepository.save(activity);

        for (int i = 0; i < 4; i++) {
            Timeslot t = new Timeslot();
            t.setActivity(activity);
            timeslotRepository.save(t);
        }

        List<Timeslot> result = timeslotRepository.findAllByActivityId(activity.getId());
        assertEquals(4, result.size());
    }
}
