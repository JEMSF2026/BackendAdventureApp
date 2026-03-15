package org.example.backendadventureapp.controller;

import org.example.backendadventureapp.model.Package;
import org.example.backendadventureapp.service.PackageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PackageController.class)
class PackageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PackageService packageService;

    @Test
    void getAllPackages_returnsPackages() throws Exception {

        Package pkg = new Package();
        pkg.setId(1);
        pkg.setPackageName("TestPakke");

        when(packageService.getAllPackages())
                .thenReturn(List.of(pkg));

        mockMvc.perform(get("/packages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].packageName")
                        .value("TestPakke"));
    }

    @Test
    void getPackageById_returnsPackage() throws Exception {

        Package pkg = new Package();
        pkg.setId(1);
        pkg.setPackageName("TestPakke");

        when(packageService.getPackageById(1)).thenReturn(pkg);

        mockMvc.perform(get("/packages/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.packageName")
                        .value("TestPakke"));

    }
}