package org.example.backendadventureapp.repository;

import org.example.backendadventureapp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Optional bruges fordi der måske ikke findes en medarbejder med den email
    // Det tvinger os til at håndtere tilfældet eksplicit frem for at risikere NullPointerException
    Optional<Employee> findByEmail(String email);
}