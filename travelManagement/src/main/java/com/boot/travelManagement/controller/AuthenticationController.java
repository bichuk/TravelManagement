package com.boot.travelManagement.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boot.travelManagement.config.JwtTokenUtil;
import com.boot.travelManagement.model.ApiResponse;
import com.boot.travelManagement.model.AuthToken;
import com.boot.travelManagement.model.LoginUser;
import com.boot.travelManagement.model.User;
import com.boot.travelManagement.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/token")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userService;

	protected Logger logger = Logger.getLogger(AuthenticationController.class.getName());

	@RequestMapping(value = "/generate-token", method = RequestMethod.POST)
	public ApiResponse<AuthToken> generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
		final User user = userService.findOne(loginUser.getUsername());
		final String token = jwtTokenUtil.generateToken(user);
		return new ApiResponse<>(200, "success",new AuthToken(token, user));
	}

}
