package com.example.energyconsumption.application;

import com.example.energyconsumption.domain.Consumption;
import com.example.energyconsumption.domain.ConsumptionFixture;
import com.example.energyconsumption.domain.ConsumptionRepository;
import com.example.energyconsumption.domain.Electronic;
import com.example.energyconsumption.domain.ElectronicFixture;
import com.example.energyconsumption.domain.ElectronicRepository;
import com.example.energyconsumption.domain.EnergyConsumptionService;
import com.example.energyconsumption.domain.Status;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
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
class EnergyConsumptionApplicationServiceTest {

    @InjectMocks
    private EnergyConsumptionApplicationService energyConsumptionApplicationService;

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

        Electronic updateElectronic = electronic.toBuilder()
            .status(Status.ON)
            .build();

        Long electronicId = electronic.getId();

        Consumption consumption = ConsumptionFixture.build()
            .toBuilder()
            .id(null)
            .endTime(null)
            .electronic(updateElectronic)
            .build();

        Mockito.doReturn(electronic)
            .when(electronicRepository)
            .findBy(electronicId);

        Mockito.doReturn(updateElectronic)
            .when(electronicRepository)
            .update(updateElectronic);

        Mockito.doReturn(electronic)
            .when(electronicRepository)
            .findBy(electronicId);

        Mockito.doReturn(consumption)
            .when(consumptionRepository)
            .create(consumptionArgumentCaptor.capture());

        Mockito.doNothing()
            .when(energyConsumptionService)
            .start(consumption);

        energyConsumptionApplicationService.powerOn(electronicId);

        Assertions.assertThat(consumption)
            .usingRecursiveComparison()
                .ignoringFields("initialTime")
                .isEqualTo(consumptionArgumentCaptor.getValue());

        Mockito.verify(electronicRepository).findBy(electronicId);
        Mockito.verify(electronicRepository).update(updateElectronic);
        Mockito.verify(consumptionRepository).create(consumptionArgumentCaptor.capture());
        Mockito.verify(energyConsumptionService).start(consumption);
    }

    @Test
    @DisplayName("should thrown an exception when a electronic it's already powered on")
    void shouldThrowExceptionWhenElectronicIsPoweredOn() {

        Electronic electronic = ElectronicFixture.build()
            .toBuilder()
            .status(Status.ON)
            .build();

        Long electronicId = electronic.getId();

        Mockito.doReturn(electronic)
            .when(electronicRepository)
            .findBy(electronicId);

        String expectedMessage = "Computer is already powered on";

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(
            RuntimeException.class, () ->
                energyConsumptionApplicationService.powerOn(electronicId)
        );

        Assertions.assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    @DisplayName("should power off the electronic and stop to consume energy")
    void shouldPowerOffElectronic() {

        Electronic electronic = ElectronicFixture.build()
            .toBuilder()
            .status(Status.ON)
            .build();

        Electronic updatedElectronic = electronic.toBuilder()
            .status(Status.OFF)
            .build();

        Long electronicId = electronic.getId();

        Consumption consumption = ConsumptionFixture.build()
            .toBuilder()
            .endTime(null)
            .electronic(electronic)
            .build();

        Consumption updatedConsumption = consumption
            .toBuilder()
            .endTime(LocalDateTime.now())
                .electronic(updatedElectronic)
            .build();

        Mockito.doReturn(electronic)
            .when(electronicRepository)
            .findBy(electronicId);

        Mockito.doReturn(updatedElectronic)
            .when(electronicRepository)
            .update(updatedElectronic);

        Mockito.doReturn(consumption)
                .when(consumptionRepository)
                .findLastBy(electronicId);

        Mockito.doReturn(updatedConsumption)
            .when(consumptionRepository)
            .update(consumptionArgumentCaptor.capture());

        energyConsumptionApplicationService.powerOff(electronicId);

        Assertions.assertThat(updatedConsumption)
                .usingRecursiveComparison()
                .ignoringFields("endTime")
                .isEqualTo(consumptionArgumentCaptor.getValue());

        Mockito.verify(electronicRepository).findBy(electronicId);
        Mockito.verify(electronicRepository).update(updatedElectronic);
        Mockito.verify(consumptionRepository).findLastBy(electronicId);
        Mockito.verify(consumptionRepository).update(consumptionArgumentCaptor.getValue());
        Mockito.verify(energyConsumptionService).stop(updatedConsumption);
    }

    @Test
    @DisplayName("should thrown an exception when a electronic it's already powered off")
    void shouldThrowExceptionWhenElectronicIsPoweredOff() {

        Electronic electronic = ElectronicFixture.build()
            .toBuilder()
            .status(Status.OFF)
            .build();

        Long electronicId = electronic.getId();

        Mockito.doReturn(electronic)
            .when(electronicRepository)
            .findBy(electronicId);

        String expectedMessage = "Computer is already powered off";

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(
            RuntimeException.class, () -> energyConsumptionApplicationService.powerOff(electronicId)
        );

        Assertions.assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    @DisplayName("should find a consumption by electronic ID")
    void shouldFindConsumptionByElectronicID() {

        Electronic electronic = ElectronicFixture.build();
        Long electronicId = electronic.getId();

        Consumption firstConsumption = ConsumptionFixture.build().toBuilder()
            .electronic(electronic)
            .build();

        Consumption secondConsumption = ConsumptionFixture.build().toBuilder()
            .id(2L)
            .electronic(electronic)
            .build();

        Collection<Consumption> expectedConsumptions = new ArrayList<>();

        expectedConsumptions.add(firstConsumption);
        expectedConsumptions.add(secondConsumption);

        Mockito.doReturn(expectedConsumptions)
            .when(consumptionRepository)
            .findBy(electronicId);

        Collection<Consumption> consumptions = energyConsumptionApplicationService
            .getAllConsumptionsBy(electronicId);

        Assertions.assertThat(expectedConsumptions).hasSameElementsAs(consumptions);

        Mockito.verify(consumptionRepository).findBy(electronicId);
    }

    @Test
    @DisplayName("should find all electronics")
    void shouldFindAllElectronics() {

        Electronic computer = ElectronicFixture.build();
        Electronic shower = ElectronicFixture.build()
            .toBuilder()
            .name("Shower")
            .powerWatts(20.0)
            .build();

        Collection<Electronic> expectedElectronics = new ArrayList<>();

        expectedElectronics.add(computer);
        expectedElectronics.add(shower);

        Mockito.doReturn(expectedElectronics)
            .when(electronicRepository)
            .findAll();

        Collection<Electronic> electronics = energyConsumptionApplicationService.getAllElectronics();

        Assertions.assertThat(expectedElectronics).hasSameElementsAs(electronics);

        Mockito.verify(electronicRepository).findAll();
    }
}
