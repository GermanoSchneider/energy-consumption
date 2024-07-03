package com.example.energy_consumption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class EnergyConsumptionApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnergyConsumptionApplication.class, args);
	}

}
