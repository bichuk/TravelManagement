package com.boot.travelManagement.model;

import java.util.Date;

//@Entity
public class Trip implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	Long id;	

	String description;

	String fromLocation;
	
	String toLocation;

	Date tripDate;

	String status;
		
	Long employeeId;
	
	Long customerId;
	
	Long vehicleId;

	private User customer;

	private User driver;
	
	private Vehicle vehicle;

	public User getCustomer() {
		return customer;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public String getDescription() {
		return description;
	}

	public User getDriver() {
		return driver;
	}

	public Long getEmployeeId() {
		return employeeId;
	}
	
	
	public String getFromLocation() {
		return fromLocation;
	}

	public Long getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public String getToLocation() {
		return toLocation;
	}

	public Date getTripDate() {
		return tripDate;
	}
	

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public void setDriver(User driver) {
		this.driver = driver;
	}
	
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}
	
	
	
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}
	public void setTripDate(Date tripDate) {
		this.tripDate = tripDate;
	}
	
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
}
