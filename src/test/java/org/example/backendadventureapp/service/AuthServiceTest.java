package org.example.backendadventureapp.service;

import org.example.backendadventureapp.model.Employee;
import org.example.backendadventureapp.model.EmployeeRole;
import org.example.backendadventureapp.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    AuthService authService;

    Employee createEmployee() {
        EmployeeRole employeeRole = new EmployeeRole();
        employeeRole.setName("ADMIN");
        return new Employee(1, employeeRole, "Jens", "Hansen", "12345678", "jens@jemsadventure.dk");
    }

    @Test
    void login_returnsTrueWithCorrectPassword() {
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
        when(employeeRepository.findByEmail("unknown@email.dk")).thenReturn(Optional.empty());

        boolean result = authService.login("unknown@email.dk", "Hansen5678");

        assertFalse(result);
        verify(employeeRepository, times(1)).findByEmail("unknown@email.dk");
    }
}