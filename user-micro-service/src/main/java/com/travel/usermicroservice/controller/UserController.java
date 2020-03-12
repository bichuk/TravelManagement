package com.travel.usermicroservice.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.travel.usermicroservice.exception.ResourceNotFoundException;
import com.travel.usermicroservice.model.User;
import com.travel.usermicroservice.repository.UserRepository;

@RestController
@RequestMapping(path="/api/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@GetMapping(path="/findByUserName/{userName}/V1")
	public Optional<User> findByUserName(@PathVariable String userName) {
		// This returns a JSON or XML with the users
		return userRepository.findByUserName(userName);
	}
	
	@GetMapping(path="/login/{userName}/{password}/{userType}/V1", produces = "application/json")
	public User login(@PathVariable String userName, @PathVariable String password, @PathVariable String userType) {
		return userRepository.findByUserNameAndPasswordAndType(userName, password, userType);
	}
	
	@GetMapping(path="/all/V1")
	public @ResponseBody Iterable<User> findAll() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}
	
	@GetMapping(path = "/findByType/{userType}/V1")
	public @ResponseBody Iterable<User> findByType(@PathVariable String userType) throws Exception {
		// This returns a JSON or XML with the users
		return userRepository.findByType(userType);
	}		
	
	@GetMapping(path="/findById/{id}/V1")
	public Optional<User> findById(@PathVariable Long id) {
		// This returns a JSON or XML with the users
		return userRepository.findById(id);
	}
	
	
	@PostMapping(path="/signUp/V1", consumes = "application/json", produces = "application/json")
	public User signUp(@Valid @RequestBody User user, BindingResult result) {

		System.out.println("result has errors: " + result.hasErrors());
		
		if (result.hasErrors()) {
			new RuntimeException("Error saving user details..." + result.getAllErrors());
		}
		return userRepository.save(user);
	}
	
	@PutMapping("/updateById/{id}/V1")
    public User update(@PathVariable Long id, @Valid @RequestBody User postRequest) {		
        return userRepository.findById(id).map(User -> {
            User.setAddress(postRequest.getAddress());
            User.setAge(postRequest.getAge());
            User.setEmail(postRequest.getEmail());
            User.setFirstName(postRequest.getFirstName());
            User.setLastName(postRequest.getLastName());
            User.setPassword(postRequest.getPassword());
            User.setPhone(postRequest.getPhone());            
            User.setType(postRequest.getType());
            User.setUserName(postRequest.getUserName());
            
            return userRepository.save(User);
        }).orElseThrow(() -> new ResourceNotFoundException("UserId " + id + " not found"));
    }
	
	@DeleteMapping("/deleteById/{id}/V1")
    public ResponseEntity<?> delete(@PathVariable (value = "id") Long id) {
        return userRepository.findById(id).map(user -> {
        	userRepository.delete(user);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }
}
