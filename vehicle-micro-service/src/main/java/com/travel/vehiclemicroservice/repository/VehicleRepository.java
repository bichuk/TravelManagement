package com.travel.vehiclemicroservice.repository;
import com.travel.vehiclemicroservice.model.Vehicle;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("vehicleRepository")
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByEmployeeId(Long employeeId);
}