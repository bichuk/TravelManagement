package com.boot.travelManagement.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.boot.travelManagement.model.User;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Value("${USER-SERVICE-URL}")
	private String userServiceUrl;
	
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;
	
	@Autowired
	protected RestTemplate restTemplate;

	@Override
	public User findOne(String userName) {
		User user = restTemplate.getForObject(userServiceUrl + "/api/user/findByUserName/" + userName + "/V1", User.class);
		
		if(user == null){
			throw new UsernameNotFoundException("User not found.");
		}
		
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		user.setType("ROLE_" + user.getType().toUpperCase());
		
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = restTemplate.getForObject(userServiceUrl + "/api/user/findByUserName/" + username + "/V1", User.class);
		if(user == null){
			throw new UsernameNotFoundException("User not found.");
		}
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		user.setType("ROLE_" + user.getType().toUpperCase());
		
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getType().toUpperCase())));
	}
}
