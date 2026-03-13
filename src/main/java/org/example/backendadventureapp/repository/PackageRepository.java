package org.example.backendadventureapp.repository;

import org.example.backendadventureapp.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<Package, Integer>{

    Package findPackageById(Integer id);
}
