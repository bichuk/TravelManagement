package com.travel.tripmicroservice.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.travel.tripmicroservice.exception.ResourceNotFoundException;
import com.travel.tripmicroservice.model.Trip;
import com.travel.tripmicroservice.repository.TripRepository;



@RestController
@RequestMapping(path="/api/trip")
public class TripController {
	@Autowired
	private TripRepository tripRepository;

	@GetMapping(path="/all/V1")
	public @ResponseBody Iterable<Trip> findAll() {
		// This returns a JSON or XML with the trips
		return tripRepository.findAll();
	}
	
	@GetMapping(path="/findById/{id}/V1", produces = "application/json")
	public Optional<Trip> findById(@PathVariable Long id) {
		return tripRepository.findById(id);
	}
	
	@GetMapping(path="/findByCustomerId/{customerId}/V1", produces = "application/json")
	public ResponseEntity<Trip[]> findByCustomerId(@PathVariable Long customerId) {
		return new ResponseEntity<Trip[]>(tripRepository.findByCustomerId(customerId),HttpStatus.OK);
	}
	
	@GetMapping(path="/findByEmployeeId/{driverId}/V1", produces = "application/json")
	public ResponseEntity<Trip[]> findByEmployeeId(@PathVariable Long driverId) {
		return new ResponseEntity<Trip[]>(tripRepository.findByEmployeeId(driverId),HttpStatus.OK);
	}
	
	
	@PostMapping(path="/bookTrip/V1", consumes = "application/json", produces = "application/json")
	public Trip save(@Valid @RequestBody Trip trip, BindingResult result) {		
		
		if (result.hasErrors()) {
			new RuntimeException("Error saving trip details..." + result.getAllErrors());
		}
		return tripRepository.save(trip);
	}
	
	@PutMapping("/updateById/{id}/V1")
    public Trip update(@PathVariable Long id, @Valid @RequestBody Trip postRequest) {
        return tripRepository.findById(id).map(trip -> {
        	trip.setDescription(postRequest.getDescription());
            trip.setFromLocation(postRequest.getFromLocation());
            trip.setStatus(postRequest.getStatus());
            trip.setToLocation(postRequest.getToLocation());
            trip.setTripDate(postRequest.getTripDate());
            trip.setVehicleId(postRequest.getVehicleId());
            trip.setEmployeeId(postRequest.getEmployeeId());
            trip.setCustomerId(postRequest.getCustomerId());
            return tripRepository.save(trip);
        }).orElseThrow(() -> new ResourceNotFoundException("TripId " + id + " not found"));
    }
	
	@PostMapping(path="/cancelTrip/{id}/{status}/V1", produces = "application/json")
	public Trip cancelTrip(@PathVariable Long id, @PathVariable String status) {
        return tripRepository.findById(id).map(trip -> {        	
            trip.setStatus(status);            
            return tripRepository.save(trip);
        }).orElseThrow(() -> new ResourceNotFoundException("TripId " + id + " not found"));
    }
	
	@DeleteMapping("/deleteById/{id}/V1")
    public ResponseEntity<?> deleteTrip(@PathVariable (value = "id") Long id) {
        return tripRepository.findById(id).map(trip -> {
        	tripRepository.delete(trip);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Trip not found with id " + id));
    }
}
