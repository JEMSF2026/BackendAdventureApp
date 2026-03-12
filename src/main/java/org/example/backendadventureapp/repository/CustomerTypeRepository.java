package org.example.backendadventureapp.repository;

import org.example.backendadventureapp.model.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerTypeRepository extends JpaRepository<CustomerType, Integer> {
}
