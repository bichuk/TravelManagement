package com.boot.travelManagement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.boot.travelManagement.controller.VehicleController;
import com.boot.travelManagement.model.Vehicle;
import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.Assert;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TravelManagementApplicationTests {

	@Autowired
	private VehicleController controller;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
    private RestTemplate restTemplate;
	
	private String userServiceUrl = "http://USER-SERVICE";
	
	private String tripServiceUrl = "http://TRIP-SERVICE";
	
	private String vehicleServiceUrl = "http://VEHICLE-SERVICE";
	
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
	
	@Test
	  public void shouldReturnAllVehicles() throws Exception {
		Vehicle[] vehicles = new Vehicle[1];
		
		Vehicle vehicle = new Vehicle();
		vehicle.setEmployeeId(1L);
		vehicle.setId(1L);
		vehicle.setName("mock vehicle");
		vehicle.setRegistrationNumber("12345");
		vehicle.setType("SUV");
		
		vehicles[0] = vehicle;
		
		Mockito
        .when(restTemplate.getForObject(vehicleServiceUrl + "/api/vehicle/all/V1", Vehicle[].class))
        .thenReturn(vehicles);
		
	    this.mockMvc.perform(get("/api/vehicle/all/V1"))
	    	.andDo(print())
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("$.*", isA(ArrayList.class)));
	  }
	
	@Test
	  public void shouldReturnVehicleById() throws Exception {
		
		Vehicle vehicle = new Vehicle();
		vehicle.setEmployeeId(1L);
		vehicle.setId(1L);
		vehicle.setName("mock vehicle");
		vehicle.setRegistrationNumber("12345");
		vehicle.setType("SUV");
		
		Mockito
        .when(restTemplate.getForObject(vehicleServiceUrl + "/api/vehicle/findById/1/V1", Vehicle.class))
        .thenReturn(vehicle);
		
	    this.mockMvc.perform(get("/api/vehicle/findById/1/V1"))
	    	.andDo(print())
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("$.name").exists());
	  }
	@Test
	  public void shouldRegister() throws Exception {
		
		Vehicle vehicle = new Vehicle();
		vehicle.setEmployeeId(1L);
		vehicle.setId(1L);
		vehicle.setName("mock vehicle");
		vehicle.setRegistrationNumber("12345");
		vehicle.setType("SUV");
		
		Mockito
        .when(restTemplate.postForObject(vehicleServiceUrl + "/api/vehicle/register/V1", vehicle,
				Vehicle.class))
        .thenReturn(vehicle);
		
		mockMvc.perform(post("/api/vehicle/register/V1")
				  .content(asJsonString(vehicle))
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.name").exists())
			      .andExpect(jsonPath("$.name").value("mock vehicle"));		      
		
	  }
	
	@Test
	  public void shouldUpdateVehicle() throws Exception {
		
		Vehicle vehicle = new Vehicle();
		vehicle.setEmployeeId(1L);
		vehicle.setId(1L);
		vehicle.setName("mock vehicle");
		vehicle.setRegistrationNumber("12345");
		vehicle.setType("SUV");
		
		HttpEntity<Vehicle> requestUpdate = new HttpEntity<>(vehicle, null);
		
		Mockito
        .when(restTemplate.exchange(
				vehicleServiceUrl + "/api/vehicle/updateById/1/V1", HttpMethod.PUT, requestUpdate,
				Vehicle.class))
        .thenReturn(new ResponseEntity<>(vehicle, HttpStatus.OK));
		
		mockMvc.perform(put("/api/vehicle/updateById/1/V1")
				  .content(asJsonString(vehicle))
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.name").exists())
			      .andExpect(jsonPath("$.name").value("mock vehicle"));		      
		
	  }
	
	@Test
	  public void shouldDeleteVehicle() throws Exception {
		
		Vehicle vehicle = new Vehicle();
		vehicle.setEmployeeId(1L);
		vehicle.setId(1L);
		vehicle.setName("mock vehicle");
		vehicle.setRegistrationNumber("12345");
		vehicle.setType("SUV");
		
//		Mockito.
//        .when(restTemplate.delete(vehicleServiceUrl + "/api/vehicle/deleteById/1/V1"))
//		
//		mockMvc.perform(delete("/api/vehicle/deleteById/1/V1")
//				  .content(asJsonString(vehicle))
//			      .contentType(MediaType.APPLICATION_JSON))
//			      .andExpect(status().isOk());
		
	  }
	@Test
	  public void shouldReturnVehicleByEmployeeId() throws Exception {
		
		Vehicle vehicle = new Vehicle();
		vehicle.setEmployeeId(1L);
		vehicle.setId(1L);
		vehicle.setName("mock vehicle");
		vehicle.setRegistrationNumber("12345");
		vehicle.setType("SUV");
		
		Mockito
        .when(restTemplate.getForObject(vehicleServiceUrl + "/api/vehicle/findVehiclesByEmployeeId/1/V1", Vehicle.class))
        .thenReturn(vehicle);
		
	    this.mockMvc.perform(get("/api/vehicle/findVehiclesByEmployeeId/1/V1"))
	    	.andDo(print())
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("$.name").exists());
	  }
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
