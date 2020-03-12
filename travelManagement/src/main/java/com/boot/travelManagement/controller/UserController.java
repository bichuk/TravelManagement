package com.boot.travelManagement.controller;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.boot.travelManagement.model.Trip;
import com.boot.travelManagement.model.User;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/user")
public class UserController {

	@Value("${USER-SERVICE-URL}")
	private String userServiceUrl;
	
	@Value("${TRIP-SERVICE-URL}")
	private String tripServiceUrl;

	@Autowired
	protected RestTemplate restTemplate;

	protected Logger logger = Logger.getLogger(UserController.class.getName());
	
	@GetMapping(path = "/login/{userType}/V1", produces = "application/json")
	public User login(@RequestHeader("Authorization") String authorization, @PathVariable String userType) { 	
		logger.info("login() invoked:");
		
		String userName = "", password = "";
		userType = userType.toLowerCase();
		
		if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
		    // Authorization: Basic base64credentials
		    String base64Credentials = authorization.substring("Basic".length()).trim();
		    byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
		    String credentials = new String(credDecoded, StandardCharsets.UTF_8);
		    // credentials = username:password
		    final String[] values = credentials.split(":", 2);
		    
		    userName = values[0];
		    password = values[1];
		}

		User loggedInUser = restTemplate
				.getForObject(userServiceUrl + "/api/user/login/" + userName + "/" + password + "/" + userType + "/V1", User.class);

		return loggedInUser;
	}
	
	@GetMapping(path = "/findByUserName/{userName}/V1", produces = "application/json")
	public User findByUserName(@PathVariable String userName) { 	
		logger.info("findByUserName() invoked:");

		User user = restTemplate.getForObject(userServiceUrl + "/api/user/findByUserName/" + userName + "/V1", User.class);

		return user;
	}
	
	@GetMapping(path = "/all/V1")
	public @ResponseBody Iterable<User> findAll() {

		User[] users = null;
		Trip[] trips = null;

		logger.info("getAllUsers() invoked:");
		users = restTemplate.getForObject(userServiceUrl + "/api/user/all/V1", User[].class);

		for (int i = 0; i < users.length; i++) {
			Long UserId = users[i].getId();

			trips = restTemplate.getForObject(tripServiceUrl + "/api/trip/findByCustomerId/" + UserId + "/V1",
					Trip[].class);

			if (trips != null && trips.length > 0) {
				for (Trip trip : trips) {

					User driver = restTemplate.getForObject(
							userServiceUrl + "/api/user/findById/" + trip.getEmployeeId() + "/V1", User.class);
					
					if (driver!= null)
						trip.setDriver(driver);

				}

				users[i].setTrips(trips);
			}

		}

		if (users == null || users.length == 0)
			return null;
		else
			return Arrays.asList(users);
	}
	
	@GetMapping(path = "/findByType/{userType}/V1")
	public @ResponseBody Iterable<User> findByType(@PathVariable String userType) throws Exception {
	
		if (userType == null || userType == "") {
			throw new Exception("userType is required ");
		}
		
		User[] users = null;
		Trip[] trips = null;

		logger.info("getAllUsers() invoked:");
		users = restTemplate.getForObject(userServiceUrl + "/api/user/findByType/" + userType + "/V1", User[].class);

		for (int i = 0; i < users.length; i++) {
			Long UserId = users[i].getId();

			trips = restTemplate.getForObject(tripServiceUrl + "/api/trip/findByCustomerId/" + UserId + "/V1",
					Trip[].class);

			if (trips != null && trips.length > 0) {
				for (Trip trip : trips) {

					User driver = restTemplate.getForObject(
							userServiceUrl + "/api/user/findById/" + trip.getEmployeeId() + "/V1", User.class);
					
					if (driver!= null)
						trip.setDriver(driver);

				}

				users[i].setTrips(trips);
			}

		}

		if (users == null || users.length == 0)
			return null;
		else
			return Arrays.asList(users);
	}

	@GetMapping(path = "/findById/{id}/V1")
	public User findById(@PathVariable Long id) {
		logger.info("findById() invoked:");

		User user = restTemplate.getForObject(userServiceUrl + "/api/user/findById/" + id + "/V1", User.class);

		Trip[] trips = restTemplate.getForObject(tripServiceUrl + "/api/trip/findByCustomerId/" + id + "/V1",
				Trip[].class);
		
		if (trips != null && trips.length > 0) {
			for (Trip trip : trips) {
				User tripCustomer = restTemplate
						.getForObject(userServiceUrl + "/api/user/getById/" + trip.getCustomerId() + "/V1", User.class);
				
				if (tripCustomer != null)
					trip.setCustomer(tripCustomer);

				User driver = restTemplate.getForObject(
						userServiceUrl + "/api/user/findById/" + trip.getEmployeeId() + "/V1", User.class);
				
				if (driver != null)
					trip.setDriver(driver);

			}
			user.setTrips(trips);
		}

		return user;
	}

	@PostMapping(path = "/signUp/V1", consumes = "application/json", produces = "application/json")
	public User signUp(@Valid @RequestBody User user, BindingResult result) {

		logger.info("signUp() invoked:");
		User newUser = new User();

		System.out.println("result has errors: " + result.hasErrors());

		if (result.hasErrors()) {
			new RuntimeException("Error saving Customer details..." + result.getAllErrors());
		}
		
		try {
			newUser = restTemplate.postForObject(userServiceUrl + "/api/user/signUp/V1", user, User.class);

			return newUser;
			
		} catch (Exception e) {
			throw new RuntimeException("Error saving Customer details..." + result.getAllErrors());
		}
	}

	@PutMapping("/updateById/{id}/V1")
	public User update(@PathVariable Long id, @Valid @RequestBody User postRequest) {
		logger.info("updateById() invoked:");

		HttpEntity<User> requestUpdate = new HttpEntity<>(postRequest, null);
		ResponseEntity<User> response = restTemplate.exchange(userServiceUrl + "/api/user/updateById/" + id + "/V1",
				HttpMethod.PUT, requestUpdate, User.class);

		return response.getBody();
	}

	@DeleteMapping("/deleteById/{id}/V1")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		logger.info("delete() invoked:");

		restTemplate.delete(userServiceUrl + "/api/user/deleteById/" + id + "/V1");

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
