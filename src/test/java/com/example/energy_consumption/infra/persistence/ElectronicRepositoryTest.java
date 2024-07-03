package com.example.energy_consumption.infra.persistence;

import static com.example.energy_consumption.domain.Status.OFF;
import static com.example.energy_consumption.domain.Status.ON;
import static com.example.energy_consumption.infra.persistence.ElectronicEntityFixture.buildWith;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ElectronicRepositoryTest {

    @Autowired
    private ElectronicRepository repository;

    @Test
    @DisplayName("find all electronics in the database")
    void shouldFindAllElectronics() {

        ElectronicEntity[] expectedElectronics = {
            buildWith(1L, "Computer", 15.5, OFF),
            buildWith(2L, "Shower", 10.8, OFF),
            buildWith(3L, "Air Conditioner", 20.5, ON)
        };

        List<ElectronicEntity> electronics = repository.findAll();

        assertThat(expectedElectronics).hasSameElementsAs(electronics);
    }
}
