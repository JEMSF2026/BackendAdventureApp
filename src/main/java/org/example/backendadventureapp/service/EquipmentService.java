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

    @Autowired
    private ActivityRepository activityRepository;

    public List<Equipment> getAllEquipmentByActivityId(int activityId) {
        return equipmentRepository.findAllByActivityId(activityId);
    }
/*
    public Equipment getEquipmentById(int equipmentId) {
        return equipmentRepository.findById(equipmentId).orElseThrow();
    }
*/
    public Equipment saveEquipment(Equipment equipment) {
        Activity activity = activityRepository.findById(equipment.getActivity().getId()).orElseThrow();
        EquipmentState equipmentState = equipmentStateRepository.findById(equipment.getEquipmentState().getId()).orElseThrow();
        equipment.setActivity(activity);
        equipment.setEquipmentState(equipmentState);
        return equipmentRepository.save(equipment);
    }

    public List<EquipmentState> getAllEquipmentStates() {
        return equipmentStateRepository.findAll();
    }

    public void deleteEquipment(int equipmentId) {
        equipmentRepository.deleteById(equipmentId);
    }

    public Equipment updateEquipment(Equipment equipment) {
        Equipment existing = equipmentRepository.findById(equipment.getId())
                .orElseThrow();

        existing.setName(equipment.getName());
        existing.setDescription(equipment.getDescription());

        existing.setActivity(activityRepository.findById(equipment.getActivity().getId())
                .orElseThrow());
        existing.setEquipmentState(equipmentStateRepository.findById(equipment.getEquipmentState().getId())
                .orElseThrow());

        return equipmentRepository.save(existing);
    }
}
