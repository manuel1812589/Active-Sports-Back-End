package com.example.demo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.SpringServletContainerInitializer;

@SpringBootApplication
public class PamelaStoreApplication extends SpringServletContainerInitializer{
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(PamelaStoreApplication.class, args);
	}

}
