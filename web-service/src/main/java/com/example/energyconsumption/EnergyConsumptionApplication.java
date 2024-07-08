package com.example.energyconsumption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class EnergyConsumptionApplication implements CommandLineRunner {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(EnergyConsumptionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		jdbcTemplate.execute("INSERT INTO t_electronic VALUES (1, 'Computer', 15.5, 'OFF')");
		jdbcTemplate.execute("INSERT INTO t_electronic VALUES (2, 'Shower', 10.8, 'OFF')");
		jdbcTemplate.execute("INSERT INTO t_electronic VALUES (3, 'Air Conditioner', 20.5, 'OFF')");
	}
}
