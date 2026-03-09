package org.example.backendadventureapp.repository;

import org.example.backendadventureapp.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, String> {
    List<Equipment> findAllByActivityId(int activityId);
}
