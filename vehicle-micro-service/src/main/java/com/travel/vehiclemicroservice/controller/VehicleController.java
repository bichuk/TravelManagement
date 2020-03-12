package com.travel.vehiclemicroservice.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.travel.vehiclemicroservice.exception.ResourceNotFoundException;
import com.travel.vehiclemicroservice.model.Vehicle;
import com.travel.vehiclemicroservice.repository.VehicleRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api/vehicle") // This means URL's start with /demo (after Application path)
public class VehicleController {
	@Autowired
	private VehicleRepository vehicleRepository;

	@GetMapping(path = "/all/V1")
	public @ResponseBody List<Vehicle> getAllVehicles() {
		return vehicleRepository.findAll();
	}

	@GetMapping(path = "/findById/{id}/V1", produces = "application/json")
	public Optional<Vehicle> findById(@PathVariable Long id) {
		return vehicleRepository.findById(id);
	}

	@PostMapping(path = "/register/V1", consumes = "application/json", produces = "application/json")
	public Vehicle register(@Valid @RequestBody Vehicle vehicle, BindingResult result) {

		if (result.hasErrors()) {
			new RuntimeException("Error saving vehicle details..." + result.getAllErrors());
		}
		return vehicleRepository.save(vehicle);
	}

	@PutMapping("/updateById/{id}/V1")
	public Vehicle update(@PathVariable Long id, @Valid @RequestBody Vehicle postRequest) {
		return vehicleRepository.findById(id).map(vehicle -> {
			vehicle.setEmployeeId(postRequest.getEmployeeId());
			vehicle.setName(postRequest.getName());
			vehicle.setRegistrationNumber(postRequest.getRegistrationNumber());
			vehicle.setType(postRequest.getType());
			return vehicleRepository.save(vehicle);
		}).orElseThrow(() -> new ResourceNotFoundException("Vehicle Id " + id + " not found"));
	}

	@DeleteMapping("/deleteById/{id}/V1")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		return vehicleRepository.findById(id).map(vehicle -> {
			vehicleRepository.delete(vehicle);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id " + id));
	}
	
	@GetMapping(path = "/findVehiclesByEmployeeId/{employeeId}/V1", produces = "application/json")
	public Iterable<Vehicle> findVehiclesByEmployeeId(@PathVariable Long employeeId) {
		return vehicleRepository.findByEmployeeId(employeeId);
	}
}
