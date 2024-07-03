package com.example.energy_consumption.infra.persistence;

import static com.example.energy_consumption.domain.Status.OFF;
import static com.example.energy_consumption.infra.persistence.ConsumptionEntityFixture.buildWith;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
class ConsumptionRepositoryTest {

    @Autowired
    private ConsumptionRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DirtiesContext
    @DisplayName("create a new electronic consumption")
    void shouldCreateANewConsumption() {

        ElectronicEntity electronic = ElectronicEntityFixture.buildWith(
            1L,
            "Computer",
            15.5,
            OFF
        );

        ConsumptionEntity newConsumption = buildWith(
            1L,
            30.5,
            LocalDate.now(),
            electronic
        );

        repository.save(newConsumption);

        ConsumptionEntity consumption = entityManager.find(ConsumptionEntity.class, 1L);

        assertThat(consumption).isNotNull();
        assertThat(consumption).isEqualTo(newConsumption);
    }

    @Test
    @DirtiesContext
    @DisplayName("find a consumption by electronic ID")
    void shouldFindAConsumptionByElectronicId() {

        ElectronicEntity electronic = ElectronicEntityFixture.buildWith(
            1L,
            "Computer",
            15.5,
            OFF
        );

        ConsumptionEntity newConsumption = buildWith(
            1L,
            30.5,
            LocalDate.now(),
            electronic
        );

        entityManager.persist(newConsumption);

        Collection<ConsumptionEntity> consumptions = repository.findByElectronicId(electronic.getId());

        assertThat(consumptions).contains(newConsumption);
    }
}
