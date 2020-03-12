package com.travel.tripmicroservice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "Trip")
public class Trip implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;	
	
	@NotNull
	@Column(name = "vehicle_id")
	Long vehicleId;
	
	@NotNull
	@Column(name = "employee_id")
	Long employeeId;
	
	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	@NotNull
	@Column(name = "customer_id")
	Long customerId;

	@NotNull
	@Size(max = 50)
	String description;

	@NotNull
	@Size(max = 50)
	@Column(name = "from_location")
	String fromLocation;

	@NotNull
	@Size(max = 50)
	@Column(name = "to_location")
	String toLocation;

	@NotNull
	@Column(name = "trip_date")
	@JsonFormat(pattern="yyyy-MM-dd")
	Date tripDate;

	String status;

	public Long getCustomerId() {
		return customerId;
	}

	public String getDescription() {
		return description;
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
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	
	public void setDescription(String description) {
		this.description = description;
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
}
