package com.example.energyconsumption.infra.persistence.consumption;

import static com.example.energyconsumption.infra.persistence.consumption.ConsumptionEntityFixture.build;
import static com.example.energyconsumption.infra.persistence.consumption.ConsumptionEntityFixture.buildWith;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.energyconsumption.domain.Consumption;
import com.example.energyconsumption.domain.ConsumptionFixture;
import com.example.energyconsumption.domain.ConsumptionRepository;
import com.example.energyconsumption.infra.ConsumptionMapper;
import com.example.energyconsumption.infra.persistence.electronic.ElectronicEntity;
import com.example.energyconsumption.infra.persistence.electronic.ElectronicEntityFixture;
import com.example.energyconsumption.infra.ElectronicMapper;
import java.time.LocalDateTime;
import java.util.Collection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@Import({ConsumptionRepositoryImpl.class, ElectronicMapper.class})
class ConsumptionRepositoryTest {

    @Autowired
    private ConsumptionRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @SpyBean
    private ConsumptionMapper mapper;

    @Test
    @DirtiesContext
    @DisplayName("create a new consumption")
    void shouldCreateANewConsumption() {

        Consumption consumption = ConsumptionFixture.build();

        repository.create(consumption);

        ConsumptionEntity entity = entityManager.find(ConsumptionEntity.class, 1L);

        assertThat(entity).isNotNull();
        assertThat(mapper.fromEntity(entity)).isEqualTo(consumption);
    }

    @Test
    @DirtiesContext
    @DisplayName("find a consumption by electronic ID")
    void shouldFindAConsumptionByElectronicId() {

        ConsumptionEntity entity = build();

        entityManager.persist(entity);

        Collection<Consumption> consumption = repository.findBy(entity.getElectronic().getId());

        assertThat(consumption).hasSize(1);
        assertThat(consumption).contains(mapper.fromEntity(entity));
    }

    @Test
    @DirtiesContext
    @DisplayName("find a consumption by electronic ID")
    void shouldFindTheLastConsumptionByElectronicId() {

        ElectronicEntity electronic = ElectronicEntityFixture.build();

        ConsumptionEntity firstConsumptionEntity = buildWith(
            null,
            LocalDateTime.now().minusHours(1),
            LocalDateTime.now().minusMinutes(30),
            electronic
        );

        ConsumptionEntity lastConsumptionEntity = buildWith(
            null,
            LocalDateTime.now(),
            null,
            electronic
        );

        entityManager.persist(firstConsumptionEntity);
        entityManager.persist(lastConsumptionEntity);

        Consumption expectedConsumption = mapper.fromEntity(lastConsumptionEntity)
            .toBuilder()
            .id(2L)
            .build();

        Consumption consumption = repository.findLastBy(electronic.getId());

        assertThat(expectedConsumption).isEqualTo(consumption);
    }

    @Test
    @DirtiesContext
    @DisplayName("should update consumption data")
    void shouldUpdateConsumptionData() {

        ConsumptionEntity entity = build();

        entityManager.persist(entity);

        Consumption consumption = mapper.fromEntity(entity);

        Consumption updatedConsumption = ConsumptionFixture.build().toBuilder()
            .endTime(LocalDateTime.now())
            .build();

        Long consumptionId = consumption.getId();

        ConsumptionEntity consumptionBeforeUpdate = findBy(consumptionId);
        Assertions.assertThat(consumption).isEqualTo(mapper.fromEntity(consumptionBeforeUpdate));

        repository.update(updatedConsumption);

        ConsumptionEntity consumptionAfterUpdate = findBy(consumptionId);
        Assertions.assertThat(updatedConsumption).isEqualTo(mapper.fromEntity(consumptionAfterUpdate));
    }

    private ConsumptionEntity findBy(Long id) {

        return entityManager.find(ConsumptionEntity.class, id);
    }
}
