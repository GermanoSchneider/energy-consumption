package com.example.energyconsumption.application;

import com.example.energyconsumption.domain.Consumption;
import com.example.energyconsumption.domain.ConsumptionFixture;
import com.example.energyconsumption.domain.ConsumptionRepository;
import com.example.energyconsumption.domain.Electronic;
import com.example.energyconsumption.domain.ElectronicFixture;
import com.example.energyconsumption.domain.ElectronicRepository;
import com.example.energyconsumption.domain.EnergyConsumptionService;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class EnergyConsumptionApplicationTest {

    @InjectMocks
    private ElectronicApplicationService electronicApplicationService;

    @Mock
    private ElectronicRepository electronicRepository;

    @Mock
    private ConsumptionRepository consumptionRepository;

    @Mock
    private EnergyConsumptionService energyConsumptionService;

    @Captor
    ArgumentCaptor<Consumption> consumptionArgumentCaptor;

    @Test
    @DisplayName("should power on the electronic and start to consume energy")
    void shouldPowerOnElectronic() {

        Electronic electronic = ElectronicFixture.build();

        Long electronicId = electronic.getId();

        Consumption consumption = ConsumptionFixture.build()
            .toBuilder()
            .id(null)
            .endTime(null)
            .electronic(electronic)
            .build();

        Mockito.doReturn(Optional.of(electronic))
            .when(electronicRepository)
            .findBy(electronicId);

        Mockito.doReturn(consumption)
            .when(consumptionRepository)
            .create(consumptionArgumentCaptor.capture());

        Mockito.doNothing()
            .when(energyConsumptionService)
            .start(consumption);

        electronicApplicationService.powerOn(electronicId);

        Assertions.assertThat(consumption)
            .usingRecursiveComparison()
                .ignoringFields("initialTime")
                .isEqualTo(consumptionArgumentCaptor.getValue());

        Mockito.verify(electronicRepository).findBy(electronicId);
        Mockito.verify(consumptionRepository).create(consumptionArgumentCaptor.capture());
        Mockito.verify(energyConsumptionService).start(consumption);
    }

    @Test
    @DisplayName("should thrown an exception when a electronic could not be found")
    void shouldThrowExceptionWhenElectronicCouldNotBeFound() {

    }

    @Test
    @DisplayName("should thrown an exception a electronic it's already powered on")
    void shouldThrowExceptionWhenElectronicIsPoweredOn() {

    }

    @Test
    @DisplayName("should power off the electronic and stop to consume energy")
    void shouldPowerOffElectronic() {

    }

    @Test
    @DisplayName("should find a consumption by electronic ID")
    void shouldFindConsumptionByElectronicID() {

    }

    @Test
    @DisplayName("should find all electronics")
    void shouldFindAllElectronics() {

    }
}
