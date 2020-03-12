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


import com.boot.travelManagement.model.Trip;
import com.boot.travelManagement.model.User;
import com.boot.travelManagement.model.Vehicle;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="/api/trip")
public class TripController {
	@Autowired
    protected RestTemplate restTemplate;
	
	@Value("${USER-SERVICE-URL}")
	private String userServiceUrl;
	
	@Value("${TRIP-SERVICE-URL}")
	private String tripServiceUrl;
	
	@Value("${VEHICLE-SERVICE-URL}")
	private String vehicleServiceUrl;
	
	protected Logger logger = Logger.getLogger(TripController.class
            .getName());


	@GetMapping(path="/all/V1")
	public @ResponseBody Iterable<Trip> getAllTrips() {
		Trip[] trips = null;
        
        logger.info("getAllTrips() invoked:");
        trips = restTemplate.getForObject(tripServiceUrl + "/api/trip/all/V1",
        		Trip[].class);
        
        for (int i = 0; i < trips.length; i++) {
        	Long customerId = trips[i].getCustomerId();
        	Long employeeId = trips[i].getEmployeeId();
        	Long vehicleId = trips[i].getVehicleId();
        	
        	User customer = restTemplate.getForObject(userServiceUrl + "/api/user/findById/" + customerId + "/V1",
        			User.class);
        	
        	User employee = restTemplate.getForObject(userServiceUrl + "/api/user/findById/" + employeeId + "/V1",
        			User.class);
        	
        	Vehicle vehicle = restTemplate.getForObject(vehicleServiceUrl + "/api/vehicle/findById/" + vehicleId + "/V1",
        			Vehicle.class);
        	
        	trips[i].setCustomer(customer);
        	trips[i].setDriver(employee);
        	trips[i].setVehicle(vehicle);
		}
        
        if (trips == null || trips.length == 0)
            return null;
        else
            return Arrays.asList(trips);
	}
	
	@GetMapping(path="/findById/{id}/V1", produces = "application/json")
	public Trip findById(@PathVariable Long id) {
		logger.info("findById() invoked:");
		
		Trip trip = restTemplate.getForObject(tripServiceUrl + "/api/trip/findById/" + id + "/V1",
        		Trip.class);
		
		User customer = restTemplate.getForObject(userServiceUrl + "/api/user/findById/" + trip.getCustomerId() + "/V1",
    			User.class);
    	
    	User employee = restTemplate.getForObject(userServiceUrl + "/api/user/findById/" + trip.getEmployeeId() + "/V1",
    			User.class);
    	
    	Vehicle vehicle = restTemplate.getForObject(vehicleServiceUrl + "/api/vehicle/findById/" + trip.getVehicleId() + "/V1",
    			Vehicle.class);
    	
    	trip.setCustomer(customer);
    	trip.setDriver(employee);
    	trip.setVehicle(vehicle);
		
		return trip;
	}
	
	@GetMapping(path="/findByCustomerId/{customerId}/V1", produces = "application/json")
	public ResponseEntity<Trip[]> findByCustomerId(@PathVariable Long customerId) {
		Trip[] trips = restTemplate.getForObject(tripServiceUrl + "/api/trip/findByCustomerId/" + customerId + "/V1",
        		Trip[].class);
		
		if (trips != null && trips.length > 0) {
			for (Trip trip : trips) {

				User customer = restTemplate.getForObject(userServiceUrl + "/api/user/findById/" + trip.getCustomerId() + "/V1",
		    			User.class);
		    	
		    	User employee = restTemplate.getForObject(userServiceUrl + "/api/user/findById/" + trip.getEmployeeId() + "/V1",
		    			User.class);
		    	
		    	Vehicle vehicle = restTemplate.getForObject(vehicleServiceUrl + "/api/vehicle/findById/" + trip.getVehicleId() + "/V1",
		    			Vehicle.class);
		    	
		    	trip.setCustomer(customer);
		    	trip.setDriver(employee);
		    	trip.setVehicle(vehicle);
			}			
		}
		
		return new ResponseEntity<Trip[]>(trips,HttpStatus.OK);		
	}
	
	@GetMapping(path="/findByEmployeeId/{employeeId}/V1", produces = "application/json")
	public ResponseEntity<Trip[]> findByEmployeeId(@PathVariable Long employeeId) {
		Trip[] trips = restTemplate.getForObject(tripServiceUrl + "/api/trip/findByEmployeeId/" + employeeId + "/V1",
        		Trip[].class);
		
		if (trips != null && trips.length > 0) {
			for (Trip trip : trips) {

				User customer = restTemplate.getForObject(userServiceUrl + "/api/user/findById/" + trip.getCustomerId() + "/V1",
		    			User.class);
		    	
		    	User employee = restTemplate.getForObject(userServiceUrl + "/api/user/findById/" + trip.getEmployeeId() + "/V1",
		    			User.class);
		    	
		    	Vehicle vehicle = restTemplate.getForObject(vehicleServiceUrl + "/api/vehicle/findById/" + trip.getVehicleId() + "/V1",
		    			Vehicle.class);
		    	
		    	trip.setCustomer(customer);
		    	trip.setDriver(employee);
		    	trip.setVehicle(vehicle);
			}			
		}
		
		return new ResponseEntity<Trip[]>(trips,HttpStatus.OK);		
	}
	
	@PostMapping(path="/bookTrip/V1", consumes = "application/json", produces = "application/json")
	public Trip bookTrip(@Valid @RequestBody Trip trip, BindingResult result) {		
		
		if (result.hasErrors()) {
			new RuntimeException("Error saving trip details..." + result.getAllErrors());
		}
		Trip newTrip = restTemplate.postForObject(tripServiceUrl + "/api/trip/bookTrip/V1",trip,
        		Trip.class);
		
		return newTrip;
	}
	
	@PutMapping("/updateById/{id}/V1")
    public Trip updateById(@PathVariable Long id, @Valid @RequestBody Trip postRequest) {
		logger.info("updateById() invoked:");
		
		HttpEntity<Trip> requestUpdate = new HttpEntity<>(postRequest, null);
		ResponseEntity<Trip> response = restTemplate.exchange(tripServiceUrl + "/api/trip/updateById/" + id + "/V1", HttpMethod.PUT, requestUpdate, Trip.class);
		
		return response.getBody();
    }
	
	@PostMapping(path="/cancelTrip/{id}/{status}/V1", produces = "application/json")
	public Trip cancelTrip(@PathVariable Long id, @PathVariable String status) {
		logger.info("cancelTrip() invoked:");
		
		Trip trip = restTemplate.postForObject(tripServiceUrl + "/api/trip/cancelTrip/" + id + "/" + status + "/V1",null,
        		Trip.class);
		
		return trip;
    }
	
	@DeleteMapping("/deleteById/{id}/V1")
    public ResponseEntity<?> deleteTrip(@PathVariable (value = "id") Long id) {
		logger.info("deleteTrip() invoked:");
		
		restTemplate.delete(tripServiceUrl + "/api/trip/deleteById/" + id + "/V1");
		
		return new ResponseEntity<>(HttpStatus.OK);
    }
}
