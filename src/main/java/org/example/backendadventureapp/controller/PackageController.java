package org.example.backendadventureapp.controller;

import org.example.backendadventureapp.model.Package;
import org.example.backendadventureapp.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:63342", "http://4.235.123.111"})
public class PackageController {

    @Autowired
    private PackageService packageService;

    @GetMapping("/packages")
    public List<Package> getAllPackages(){
        return packageService.getAllPackages();
    }

    @GetMapping("/packages/{id}")
    public Package getPackageById(@PathVariable Integer id){
        return packageService.getPackageById(id);
    }
}
