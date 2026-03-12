package org.example.backendadventureapp.service;

import org.example.backendadventureapp.model.Employee;
import org.example.backendadventureapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public boolean login(String email, String password) {
        // Optional bruges fordi findByEmail kan returnere null hvis ingen medarbejder matcher
        // Optional tvinger os til at håndtere tilfældet eksplicit frem for at få en NullPointerException
        Optional<Employee> employee = employeeRepository.findByEmail(email);

        if (employee.isEmpty()) {
            return false;
        }

        // Adgangskoden er medarbejderens efternavn + de 4 sidste cifre af telefonnummeret
        // Eks: efternavn="Hansen", telefon="12345678" → adgangskode="Hansen5678"
        String lastName = employee.get().getLastName();
        String phoneNumber = employee.get().getPhoneNumber();
        String expectedPassword = lastName + phoneNumber.substring(phoneNumber.length() - 4);

        return password.equals(expectedPassword);
    }
}