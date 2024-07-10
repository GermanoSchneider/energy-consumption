package com.example.energyconsumption.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class DataLoader implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        jdbcTemplate.execute("INSERT IGNORE INTO t_electronic VALUES (1, 'Air Conditioner', 2.0, 'OFF')");
        jdbcTemplate.execute("INSERT IGNORE INTO t_electronic VALUES (2, 'Refrigerator', 0.3, 'OFF')");
        jdbcTemplate.execute("INSERT IGNORE INTO t_electronic VALUES (3, 'Television', 0.15, 'OFF')");
        jdbcTemplate.execute("INSERT IGNORE INTO t_electronic VALUES (4, 'Microwave Oven', 1.25, 'OFF')");
        jdbcTemplate.execute("INSERT IGNORE INTO t_electronic VALUES (5, 'Dishwasher', 1.35, 'OFF')");
        jdbcTemplate.execute("INSERT IGNORE INTO t_electronic VALUES (6, 'Washing Machine', 1.0, 'OFF')");
        jdbcTemplate.execute("INSERT IGNORE INTO t_electronic VALUES (7, 'Dryer', 3.0, 'OFF')");
        jdbcTemplate.execute("INSERT IGNORE INTO t_electronic VALUES (8, 'Computer (Desktop)', 0.35, 'OFF')");
        jdbcTemplate.execute("INSERT IGNORE INTO t_electronic VALUES (9, 'Laptop', 0.075, 'OFF')");
        jdbcTemplate.execute("INSERT IGNORE INTO t_electronic VALUES (10, 'Vacuum Cleaner', 1.0, 'OFF')");
        jdbcTemplate.execute("INSERT IGNORE INTO t_electronic VALUES (11, 'Electric Kettle', 2.25, 'OFF')");
        jdbcTemplate.execute("INSERT IGNORE INTO t_electronic VALUES (12, 'Hair Dryer', 1.5, 'OFF')");
        jdbcTemplate.execute("INSERT IGNORE INTO t_electronic VALUES (13, 'Electric Heater', 1.5, 'OFF')");
        jdbcTemplate.execute("INSERT IGNORE INTO t_electronic VALUES (14, 'Ceiling Fan', 0.06, 'OFF')");
        jdbcTemplate.execute("INSERT IGNORE INTO t_electronic VALUES (15, 'Light Bulb (LED)', 0.01, 'OFF')");
    }
}
