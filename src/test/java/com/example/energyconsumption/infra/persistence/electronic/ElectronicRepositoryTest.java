package com.example.energyconsumption.infra.persistence.electronic;

import static com.example.energyconsumption.domain.ElectronicFixture.buildWith;
import static com.example.energyconsumption.domain.Status.OFF;
import static com.example.energyconsumption.domain.Status.ON;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.energyconsumption.domain.Electronic;
import com.example.energyconsumption.domain.ElectronicFixture;
import com.example.energyconsumption.domain.ElectronicRepository;
import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({ElectronicRepositoryImpl.class})
class ElectronicRepositoryTest {

    @Autowired
    private ElectronicRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @SpyBean
    private ElectronicMapper mapper;

    @Test
    @DisplayName("find all electronics in the database")
    void shouldFindAllElectronics() {

        Electronic[] expectedElectronics = {
            buildWith(1L, "Computer", 15.5, OFF),
            buildWith(2L, "Shower", 10.8, OFF),
            buildWith(3L, "Air Conditioner", 20.5, ON)
        };

        Collection<Electronic> electronics = repository.findAll();

        assertThat(expectedElectronics).hasSameElementsAs(electronics);
    }

    @Test
    @DisplayName("find electronic by ID")
    void shouldFindElectronicByID() {

        Electronic expectedElectronic = ElectronicFixture.build();

        Optional<Electronic> electronic = repository.findBy(1L);

        assertThat(electronic).isNotNull();
        assertThat(expectedElectronic).isEqualTo(electronic.get());
    }
}
