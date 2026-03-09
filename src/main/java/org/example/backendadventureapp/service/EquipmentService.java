package org.example.backendadventureapp.service;

import org.example.backendadventureapp.model.Equipment;
import org.example.backendadventureapp.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    public List<Equipment> getAllEquipmentByActivityId(int activityId) {
        return equipmentRepository.findAllByActivityId(activityId);
    }
}
