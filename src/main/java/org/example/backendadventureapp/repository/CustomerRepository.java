package org.example.backendadventureapp.repository;

import org.example.backendadventureapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository  extends JpaRepository<Customer, Integer> {
}
