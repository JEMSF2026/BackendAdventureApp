package org.example.backendadventureapp.service;

import org.example.backendadventureapp.model.Activity;
import org.example.backendadventureapp.model.Equipment;
import org.example.backendadventureapp.model.EquipmentState;
import org.example.backendadventureapp.repository.ActivityRepository;
import org.example.backendadventureapp.repository.EquipmentRepository;
import org.example.backendadventureapp.repository.EquipmentStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentStateRepository equipmentStateRepository;

    public List<Equipment> getAllEquipmentByActivityId(int activityId) {
        return equipmentRepository.findAllByActivityId(activityId);
    }

    public Equipment saveEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    public List<EquipmentState> getAllEquipmentStates() {
        return equipmentStateRepository.findAll();
    }
}
