package org.example.backendadventureapp.controller;

import org.example.backendadventureapp.model.Equipment;
import org.example.backendadventureapp.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping("/equipment/{activityId}")
    public List<Equipment> getAllEquipmentByActivityId(@PathVariable int activityId) {
        return equipmentService.getAllEquipmentByActivityId(activityId);
    }
}
