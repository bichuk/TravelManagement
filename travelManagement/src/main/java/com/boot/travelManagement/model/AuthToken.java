package com.boot.travelManagement.model;

public class AuthToken {

    private String token;
    private User user;

    public AuthToken(){

    }

    public AuthToken(String token, User user){
        this.token = token;
        this.setUser(user);
    }

    public AuthToken(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
}
