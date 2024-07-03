package com.example.energyconsumption.infra.persistence.consumption;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.energyconsumption.domain.Consumption;
import com.example.energyconsumption.domain.ConsumptionFixture;
import com.example.energyconsumption.domain.ConsumptionRepository;
import com.example.energyconsumption.infra.persistence.electronic.ElectronicMapper;
import java.util.Collection;
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

        ConsumptionEntity entity = ConsumptionEntityFixture.build();

        entityManager.persist(entity);

        Collection<Consumption> consumption = repository.findBy(entity.getElectronic().getId());

        assertThat(consumption).hasSize(1);
        assertThat(consumption).contains(mapper.fromEntity(entity));
    }
}
