package com.boot.travelManagement.model;

public class User implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String userName;
	
	private String password;
	
	
	private String firstName;
	
	private String lastName;
	
	private String address;
	
	private Integer age;
	
	private String phone;
	
	private String email;
	
	
	private String type;
	
	private Trip[] trips;
	
	public String getAddress() {
		return address;
	}
	public Integer getAge() {
		return age;
	}
	public String getEmail() {
		return email;
	}
	public String getFirstName() {
		return firstName;
	}
	public Long getId() {
		return id;
	}
	public String getLastName() {
		return lastName;
	}
	public String getPassword() {
		return password;
	}
	public String getPhone() {
		return phone;
	}

	public String getType() {
		return type;
	}
	public String getUserName() {
		return userName;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setType(String type) {
		this.type = type;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Trip[] getTrips() {
		return trips;
	}
	public void setTrips(Trip[] trips) {
		this.trips = trips;
	}
}
