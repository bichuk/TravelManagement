package com.boot.travelManagement.model;

public class Vehicle implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Long id;
	
	String name;

	String type;// Car, Van, SUV

	String registrationNumber;

	Long employeeId;
	
	User employee;

	

	public User getEmployee() {
		return employee;
	}

	public void setEmployee(User employee) {
		this.employee = employee;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public String getType() {
		return type;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public void setType(String type) {
		this.type = type;
	}
}
