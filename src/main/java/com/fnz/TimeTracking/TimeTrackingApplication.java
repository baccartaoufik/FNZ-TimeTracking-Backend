package com.fnz.TimeTracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
public class TimeTrackingApplication
{

	public static void main(String[] args) {
		SpringApplication.run( TimeTrackingApplication.class, args );
	}

}
