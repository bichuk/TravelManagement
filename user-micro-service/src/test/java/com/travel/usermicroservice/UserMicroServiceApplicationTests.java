package com.travel.usermicroservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
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
import com.travel.usermicroservice.controller.UserController;
import com.travel.usermicroservice.model.User;
import com.travel.usermicroservice.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserMicroServiceApplicationTests {

	@Autowired
	private UserController controller;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserRepository mockUserRepository;
	
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
	
	@Test
	  public void greetingShouldReturnMessageFromService() throws Exception {
	    this.mockMvc.perform(get("/api/user/greeting")).andDo(print()).andExpect(status().isOk());
	        //.andExpect(content().string(containsString("Hello Mock")));
	  }
	
	@Test
	  public void shouldReturnLoggedInUser() throws Exception {
	    this.mockMvc.perform(get("/api/user/login/admin/password/admin/V1"))
	    	.andDo(print())
	    	.andExpect(status().isOk())
	        //.andExpect(content().string(containsString("Hello World")));
	    	.andExpect(jsonPath("$.firstName").value("admin"));
	  }
	
	@Test
	  public void loginShouldFail() throws Exception {
	    this.mockMvc.perform(get("/api/user/login/admin/password1/admin/V1"))
	    	.andDo(print())
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("$.firstName").doesNotExist());
	  }
	@Test
	  public void shouldFailIfNoRequiredParameters() throws Exception {
	    this.mockMvc.perform(get("/api/user/login////V1"))
	    	.andDo(print())
	    	.andExpect(status().isNotFound());
	  }
	@Test
	  public void shouldReturnAllUsers() throws Exception {
	    this.mockMvc.perform(get("/api/user/all/V1"))
	    	.andDo(print())
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("$.*", isA(ArrayList.class)));
	  }
	@Test
	  public void shouldReturnAdminRecord() throws Exception {
	    this.mockMvc.perform(get("/api/user/findByType/admin/V1"))
	    	.andDo(print())
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("$[0].type", is("admin")));
	  }
	@Test
	  public void shouldReturnUserById() throws Exception {
	    this.mockMvc.perform(get("/api/user/findById/1/V1"))
	    	.andDo(print())
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("$.firstName").exists());
	  }
	@Test
	  public void shouldSignUp() throws Exception {
		
		User user = new User();
		user.setAddress("mockAddress");
		user.setAge(30);
		user.setEmail("mock@mock.com");
		user.setFirstName("mock first name");
		user.setLastName("mock last name");
		user.setPassword("asdasd");
		user.setPhone("12345");
		user.setType("admin");
		user.setUserName("test");
		
		when(mockUserRepository.save(Mockito.any())).thenReturn(user);
		
		mockMvc.perform(post("/api/user/signUp/V1")
				  .content(asJsonString(user))
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.firstName").exists())
			      .andExpect(jsonPath("$.firstName").value("mock first name"));		      
		
	  }
	
	@Test
	  public void shouldUpdateUser() throws Exception {
		
		User user = new User();
		user.setAddress("mockAddress");
		user.setAge(30);
		user.setEmail("mock@mock.com");
		user.setFirstName("mock first name");
		user.setLastName("mock last name");
		user.setPassword("asdasd");
		user.setPhone("12345");
		user.setType("admin");
		user.setUserName("test");
		user.setId(1L);
		
		when(mockUserRepository.save(Mockito.any())).thenReturn(user);
		when(mockUserRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
		
		mockMvc.perform(put("/api/user/updateById/1/V1")
				  .content(asJsonString(user))
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.firstName").exists())
			      .andExpect(jsonPath("$.firstName").value("mock first name"));		      
		
	  }
	
	@Test
	  public void shouldDeleteUser() throws Exception {
		
		User user = new User();
		user.setAddress("mockAddress");
		user.setAge(30);
		user.setEmail("mock@mock.com");
		user.setFirstName("mock first name");
		user.setLastName("mock last name");
		user.setPassword("asdasd");
		user.setPhone("12345");
		user.setType("admin");
		user.setUserName("test");
		user.setId(1L);
		
		when(mockUserRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
		mockUserRepository.delete(Mockito.any(User.class));
		
		mockMvc.perform(delete("/api/user/deleteById/1/V1")
				  .content(asJsonString(user))
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk());
		
	  }
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
