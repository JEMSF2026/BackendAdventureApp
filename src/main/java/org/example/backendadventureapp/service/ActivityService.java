package org.example.backendadventureapp.service;

import org.example.backendadventureapp.model.Activity;
import org.example.backendadventureapp.repository.ActivityRepository;
import org.example.backendadventureapp.repository.EquipmentRepository;
import org.example.backendadventureapp.repository.PackageRepository;
import org.example.backendadventureapp.repository.TimeslotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private TimeslotRepository timeslotRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private PackageRepository packageRepository;

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

    public void deleteActivityById(int id) {
        // Tjek om aktiviteten har tidsrum med en aktiv reservation – afvis sletning hvis ja
        timeslotRepository.findAllByActivityId(id).stream()
                .filter(ts -> ts.getReservation() != null)
                .findFirst()
                .ifPresent(ts -> {
                    throw new IllegalStateException(
                            "Aktiviteten kan ikke slettes, da bookingnummer "
                            + ts.getReservation().getBookingNumber()
                            + " har booket aktiviteten."
                    );
                });

        // 1. Slet tidsrum tilknyttet aktiviteten
        timeslotRepository.deleteAll(timeslotRepository.findAllByActivityId(id));

        // 2. Slet udstyr tilknyttet aktiviteten
        equipmentRepository.deleteAll(equipmentRepository.findAllByActivityId(id));

        // 3. Fjern aktiviteten fra alle firmapakker (many-to-many join-tabel)
        packageRepository.findAll().forEach(pkg -> {
            pkg.getActivities().removeIf(a -> a.getId().equals(id));
            packageRepository.save(pkg);
        });

        // 4. Slet selve aktiviteten
        activityRepository.deleteById(id);
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
