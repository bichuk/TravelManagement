package com.travel.tripmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TripMicroServiceApplication {

	public static void main(String[] args) {
		//System.setProperty("spring.config.name", "trip-server");
		SpringApplication.run(TripMicroServiceApplication.class, args);
	}

}
