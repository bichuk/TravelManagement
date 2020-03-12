package com.boot.travelManagement.service;

import com.boot.travelManagement.model.User;

public interface UserService {
	User findOne(String username);
}
