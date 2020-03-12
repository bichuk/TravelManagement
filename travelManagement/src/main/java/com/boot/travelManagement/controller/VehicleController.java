package com.boot.travelManagement.controller;

import java.util.Arrays;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.client.RestTemplate;

import com.boot.travelManagement.model.User;
import com.boot.travelManagement.model.Vehicle;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api/vehicle") // This means URL's start with /demo (after Application path)
public class VehicleController {
	@Autowired
	protected RestTemplate restTemplate;

	@Value("${USER-SERVICE-URL}")
	private String userServiceUrl;
	
	@Value("${TRIP-SERVICE-URL}")
	private String tripServiceUrl;
	
	@Value("${VEHICLE-SERVICE-URL}")
	private String vehicleServiceUrl;
	

	protected Logger logger = Logger.getLogger(TripController.class.getName());

	@GetMapping(path = "/all/V1")
	public @ResponseBody Iterable<Vehicle> getAllVehicles() {
		Vehicle[] vehicles = null;

		logger.info("getAllVehicles() invoked:");
		vehicles = restTemplate.getForObject(vehicleServiceUrl + "/api/vehicle/all/V1", Vehicle[].class);
		
		for (Vehicle vehicle : vehicles) {

			User driver = restTemplate.getForObject(
					userServiceUrl + "/api/user/findById/" + vehicle.getEmployeeId() + "/V1", User.class);
			
			if (driver!= null)
				vehicle.setEmployee(driver);

		}
		
		if (vehicles == null || vehicles.length == 0)
			return null;
		else
			return Arrays.asList(vehicles);
	}

	@GetMapping(path = "/vehicleById/{id}/V1", produces = "application/json")
	public Vehicle findVehicleById(@PathVariable Long id) {
		logger.info("findVehicleById() invoked:");

		Vehicle vehicle = restTemplate.getForObject(vehicleServiceUrl + "/api/vehicle/findById/" + id + "/V1",
				Vehicle.class);

		return vehicle;
	}

	@PostMapping(path = "/register/V1", consumes = "application/json", produces = "application/json")
	public Vehicle registerVehicle(@Valid @RequestBody Vehicle vehicle, BindingResult result) {

		if (result.hasErrors()) {
			new RuntimeException("Error saving vehicle details..." + result.getAllErrors());
		}
		Vehicle newVehicle = restTemplate.postForObject(vehicleServiceUrl + "/api/vehicle/register/V1", vehicle,
				Vehicle.class);

		return newVehicle;
	}

	@PutMapping("/updateById/{id}/V1")
	public Vehicle updateById(@PathVariable Long id, @Valid @RequestBody Vehicle postRequest) {
		logger.info("updateVehicle() invoked:");

		HttpEntity<Vehicle> requestUpdate = new HttpEntity<>(postRequest, null);
		ResponseEntity<Vehicle> response = restTemplate.exchange(
				vehicleServiceUrl + "/api/vehicle/updateById/" + id + "/V1", HttpMethod.PUT, requestUpdate,
				Vehicle.class);

		return response.getBody();
	}

	@DeleteMapping("/deleteById/{id}/V1")
	public ResponseEntity<?> deleteVehicle(@PathVariable(value = "id") Long id) {
		logger.info("deleteVehicle() invoked:");

		restTemplate.delete(vehicleServiceUrl + "/api/vehicle/deleteById/" + id + "/V1");

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(path = "/findVehiclesByEmployeeId/{employeeId}/V1", produces = "application/json")
	public Iterable<Vehicle> findVehiclesByEmployeeId(@PathVariable Long employeeId) {
		Vehicle[] vehicles = null;

		logger.info("findVehicleById() invoked:");

		vehicles = restTemplate.getForObject(
				vehicleServiceUrl + "/api/vehicle/findVehiclesByEmployeeId/" + employeeId + "/V1", Vehicle[].class);

		if (vehicles == null || vehicles.length == 0)
			return null;
		else
			return Arrays.asList(vehicles);

	}
}
