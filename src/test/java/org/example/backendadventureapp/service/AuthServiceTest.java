package org.example.backendadventureapp.service;

import org.example.backendadventureapp.model.Employee;
import org.example.backendadventureapp.model.EmployeeRole;
import org.example.backendadventureapp.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    AuthService authService;


    // Hjælpemetode til at oprette en testmedarbejder
    Employee createEmployee() {
        EmployeeRole employeeRole = new EmployeeRole();
        employeeRole.setName("ADMIN");
        return new Employee(1, employeeRole, "Jens", "Hansen", "12345678", "jens@jemsadventure.dk");
    }

    @Test
    void login_returnsTrueWithCorrectPassword() {
        // Password is lastName + last 4 digits of phoneNumber = "Hansen5678"
        when(employeeRepository.findByEmail("jens@jemsadventure.dk")).thenReturn(Optional.of(createEmployee()));

        boolean result = authService.login("jens@jemsadventure.dk", "Hansen5678");

        assertTrue(result);
        verify(employeeRepository, times(1)).findByEmail("jens@jemsadventure.dk");
    }

    @Test
    void login_returnsFalseWithWrongPassword() {
        when(employeeRepository.findByEmail("jens@jemsadventure.dk")).thenReturn(Optional.of(createEmployee()));

        boolean result = authService.login("jens@jemsadventure.dk", "WrongPassword");

        assertFalse(result);
        verify(employeeRepository, times(1)).findByEmail("jens@jemsadventure.dk");
    }

    @Test
    void login_returnsFalseWithUnknownEmail() {
        // No employee with this email exists in the system
        when(employeeRepository.findByEmail("unknown@email.dk")).thenReturn(Optional.empty());

        boolean result = authService.login("unknown@email.dk", "Hansen5678");

        assertFalse(result);
        verify(employeeRepository, times(1)).findByEmail("unknown@email.dk");
    }
}