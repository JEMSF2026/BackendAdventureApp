package org.example.backendadventureapp.repository;

import org.example.backendadventureapp.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, String> {
}
