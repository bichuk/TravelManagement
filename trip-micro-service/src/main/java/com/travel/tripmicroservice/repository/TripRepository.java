package com.travel.tripmicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.tripmicroservice.model.Trip;



@Repository("tripRepository")
public interface TripRepository extends JpaRepository<Trip, Long>{

	Trip[] findByCustomerId(Long customerId);

	Trip[] findByEmployeeId(Long employeeId);

}
