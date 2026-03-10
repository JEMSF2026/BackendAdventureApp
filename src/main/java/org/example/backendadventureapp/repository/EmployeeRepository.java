package org.example.backendadventureapp.repository;

import org.example.backendadventureapp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
