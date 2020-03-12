package com.travel.tripmicroservice;

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
import java.util.Date;
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
import com.travel.tripmicroservice.controller.TripController;
import com.travel.tripmicroservice.model.Trip;
import com.travel.tripmicroservice.repository.TripRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class TripMicroServiceApplicationTests {

	@Autowired
	private TripController controller;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TripRepository mockTripRepository;
	
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
	
	
	@Test
	  public void shouldReturnAllTrips() throws Exception {
	    this.mockMvc.perform(get("/api/trip/all/V1"))
	    	.andDo(print())
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("$.*", isA(ArrayList.class)));
	  }
	
	@Test
	  public void shouldReturnTripById() throws Exception {
	    this.mockMvc.perform(get("/api/trip/findById/1/V1"))
	    	.andDo(print())
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("$.description").exists());
	  }
	
	@Test
	  public void shouldReturnTripByCustomerId() throws Exception {
	    this.mockMvc.perform(get("/api/trip/findByCustomerId/1/V1"))
	    	.andDo(print())
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("$.description").exists());
	  }
	
	@Test
	  public void shouldReturnTripByEmployeeId() throws Exception {
	    this.mockMvc.perform(get("/api/trip/findByEmployeeId/1/V1"))
	    	.andDo(print())
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("$.description").exists());
	  }
	
	@Test
	  public void shouldBookTrip() throws Exception {
		
		Trip trip = new Trip();
		trip.setCustomerId(1L);
		trip.setDescription("mock desc");
		trip.setEmployeeId(1L);
		trip.setFromLocation("mock locaton");
		trip.setId(1L);
		trip.setStatus("Confirmed");
		trip.setToLocation("mock to location");
		trip.setTripDate(new Date());
		trip.setVehicleId(1L);
		
		when(mockTripRepository.save(Mockito.any())).thenReturn(trip);
		
		mockMvc.perform(post("/api/trip/bookTrip/V1")
				  .content(asJsonString(trip))
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.description").exists())
			      .andExpect(jsonPath("$.description").value("mock desc"));		      
		
	  }
	
	@Test
	  public void shouldUpdateTrip() throws Exception {
		
		Trip trip = new Trip();
		trip.setCustomerId(1L);
		trip.setDescription("mock desc");
		trip.setEmployeeId(1L);
		trip.setFromLocation("mock locaton");
		trip.setId(1L);
		trip.setStatus("Confirmed");
		trip.setToLocation("mock to location");
		trip.setTripDate(new Date());
		trip.setVehicleId(1L);
		
		when(mockTripRepository.save(Mockito.any())).thenReturn(trip);
		when(mockTripRepository.findById(Mockito.any())).thenReturn(Optional.of(trip));
		
		mockMvc.perform(put("/api/trip/updateById/1/V1")
				  .content(asJsonString(trip))
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.description").exists())
			      .andExpect(jsonPath("$.description").value("mock desc"));		      
		
	  }
	
	@Test
	  public void shouldDeleteTrip() throws Exception {
		
		Trip trip = new Trip();
		trip.setCustomerId(1L);
		trip.setDescription("mock desc");
		trip.setEmployeeId(1L);
		trip.setFromLocation("mock locaton");
		trip.setId(1L);
		trip.setStatus("Confirmed");
		trip.setToLocation("mock to location");
		trip.setTripDate(new Date());
		trip.setVehicleId(1L);
		
		
		when(mockTripRepository.findById(Mockito.any())).thenReturn(Optional.of(trip));
		mockTripRepository.delete(Mockito.any(Trip.class));
		
		mockMvc.perform(delete("/api/trip/deleteById/1/V1")
				  .content(asJsonString(trip))
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk());
		
	  }
	@Test
	  public void shouldCancelTrip() throws Exception {
		
		Trip trip = new Trip();
		trip.setCustomerId(1L);
		trip.setDescription("mock desc");
		trip.setEmployeeId(1L);
		trip.setFromLocation("mock locaton");
		trip.setId(1L);
		trip.setStatus("Confirmed");
		trip.setToLocation("mock to location");
		trip.setTripDate(new Date());
		trip.setVehicleId(1L);
		
		when(mockTripRepository.save(Mockito.any())).thenReturn(trip);
		when(mockTripRepository.findById(Mockito.any())).thenReturn(Optional.of(trip));
		
		mockMvc.perform(put("/api/trip/cancelTrip/1/1/V1")
				  .content(asJsonString(trip))
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.description").exists())
			      .andExpect(jsonPath("$.description").value("mock desc"));		      
		
	  }
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
