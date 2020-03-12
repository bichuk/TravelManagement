package com.travel.vehiclemicroservice;

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

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.vehiclemicroservice.controller.VehicleController;
import com.travel.vehiclemicroservice.model.Vehicle;
import com.travel.vehiclemicroservice.repository.VehicleRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class VehicleMicroServiceApplicationTests {

	@Autowired
	private VehicleController controller;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private VehicleRepository mockVehicleRepository;
	
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
	
	@Test
	  public void shouldReturnAllVehicles() throws Exception {
	    this.mockMvc.perform(get("/api/vehicle/all/V1"))
	    	.andDo(print())
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("$.*", isA(ArrayList.class)));
	  }
	
	@Test
	  public void shouldReturnVehicleById() throws Exception {
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
		
		when(mockVehicleRepository.save(Mockito.any())).thenReturn(vehicle);
		
		mockMvc.perform(post("/api/vehicle/signUp/V1")
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
		
		when(mockVehicleRepository.save(Mockito.any())).thenReturn(vehicle);
		when(mockVehicleRepository.findById(Mockito.any())).thenReturn(Optional.of(vehicle));
		
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
		
		when(mockVehicleRepository.findById(Mockito.any())).thenReturn(Optional.of(vehicle));
		mockVehicleRepository.delete(Mockito.any(Vehicle.class));
		
		mockMvc.perform(delete("/api/vehicle/deleteById/1/V1")
				  .content(asJsonString(vehicle))
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk());
		
	  }
	@Test
	  public void shouldReturnVehicleByEmployeeId() throws Exception {
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
