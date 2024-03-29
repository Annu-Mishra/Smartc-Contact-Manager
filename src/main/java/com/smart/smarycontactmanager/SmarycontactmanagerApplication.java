package com.smart.smarycontactmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
// import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@EntityScan(basePackages = {"com.smart.smarycontactmanager.smartentities"})
@ComponentScan(basePackages = {"com.smart.smarycontactmanager.smartcontroller","com.smart.smarycontactmanager.config","com.smart.smarycontactmanager.smartdao"}) 
public class SmarycontactmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmarycontactmanagerApplication.class, args);
	}

}
// b36b47e4-aba6-4ab6-bae2-70184fde22af