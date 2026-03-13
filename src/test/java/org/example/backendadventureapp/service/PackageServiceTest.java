package org.example.backendadventureapp.service;

import org.example.backendadventureapp.repository.PackageRepository;
import org.example.backendadventureapp.model.Package;
import org.glassfish.jaxb.runtime.v2.runtime.reflect.Lister;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PackageServiceTest {

    @Mock
    private PackageRepository packageRepository;

    @InjectMocks
    private PackageService packageService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllPackages_returnsAllPackages() {

        Package pkg1 = new Package();
        pkg1.setId(1);
        pkg1.setPackageName("TestPakke1");

        Package pkg2 = new Package();
        pkg2.setId(2);
        pkg2.setPackageName("TestPakke2");

        List<Package> packages = List.of(pkg1, pkg2);

        when(packageRepository.findAll()).thenReturn(packages);

        List<Package> result = packageService.getAllPackages();

        assertEquals(2, result.size());
        assertEquals("TestPakke1", result.get(0).getPackageName());

        verify(packageRepository, times(1)).findAll();
    }

    @Test
    void getPackageById_returnsPackage() {

        Package pkg = new Package();
        pkg.setId(1);
        pkg.setPackageName("TestPakke");

        when(packageRepository.findPackageById(1)).thenReturn(pkg);

        Package result = packageService.getPackageById(1);

        assertEquals("TestPakke", result.getPackageName());

        verify(packageRepository).findPackageById(1);
    }
}