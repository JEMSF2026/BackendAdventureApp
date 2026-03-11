package org.example.backendadventureapp.controller;

import org.example.backendadventureapp.model.Equipment;
import org.example.backendadventureapp.model.EquipmentState;
import org.example.backendadventureapp.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(
        origins = "http://localhost:63342",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT}
)
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping("/equipment/{activityId}")
    public List<Equipment> getAllEquipmentByActivityId(@PathVariable int activityId) {
        return equipmentService.getAllEquipmentByActivityId(activityId);
    }
/*
    @GetMapping("/equipment/{equipmentId}")
    public Equipment getEquipmentById(@PathVariable int equipmentId) {
        return equipmentService.getEquipmentById(equipmentId);
    }
*/
    @GetMapping("/equipmentStates")
    public List<EquipmentState> getAllEquipmentStates() {
        return equipmentService.getAllEquipmentStates();
    }

    @PostMapping("/equipment/save")
    public Equipment saveEquipment(@RequestBody Equipment equipment) {
        return equipmentService.saveEquipment(equipment);
    }

    @DeleteMapping("/equipment/delete/{equipmentId}")
    public void deleteEquipment(@PathVariable int equipmentId) {
        equipmentService.deleteEquipment(equipmentId);
    }

    @PutMapping("/equipment/update/{equipmentId}")
    public Equipment updateEquipment(@PathVariable int equipmentId, @RequestBody Equipment equipment) {
        equipment.setId(equipmentId);
        return equipmentService.updateEquipment(equipment);
    }
}
