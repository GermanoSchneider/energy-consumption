package com.example.energyconsumption.application;

import com.example.energyconsumption.domain.Consumption;
import com.example.energyconsumption.domain.ConsumptionRepository;
import com.example.energyconsumption.domain.Electronic;
import com.example.energyconsumption.domain.ElectronicRepository;
import com.example.energyconsumption.domain.EnergyConsumptionService;
import com.example.energyconsumption.domain.Status;
import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.stereotype.Service;

@Service
public class EnergyConsumptionApplicationService {

    private final ElectronicRepository electronicRepository;

    private final ConsumptionRepository consumptionRepository;

    private final EnergyConsumptionService energyConsumptionService;

    public EnergyConsumptionApplicationService(
        ElectronicRepository electronicRepository,
        ConsumptionRepository consumptionRepository,
        EnergyConsumptionService energyConsumptionService
    ) {
        this.electronicRepository = electronicRepository;
        this.consumptionRepository = consumptionRepository;
        this.energyConsumptionService = energyConsumptionService;
    }

    public void powerOn(Long electronicId) {

        Electronic electronic = electronicRepository.findBy(electronicId);
        if (electronic.isOn()) throw new RuntimeException(electronic.getName() + " is already powered on");

        Electronic electronicWithStatusOn = electronic.toBuilder().status(Status.ON).build();
        Electronic updatedElectronic = electronicRepository.update(electronicWithStatusOn);

        Consumption consumption = Consumption.builder()
            .initialTime(LocalDateTime.now())
            .electronic(updatedElectronic)
            .build();

        Consumption createdConsumption = consumptionRepository.create(consumption);
        energyConsumptionService.start(createdConsumption);
    }

    public Collection<Electronic> getAllElectronics() {

        return electronicRepository.findAll();
    }

    public Collection<Consumption> getAllConsumptionsBy(Long electronicId) {

        return consumptionRepository.findBy(electronicId);
    }

    public void powerOff(Long electronicId) {

        Electronic electronic = electronicRepository.findBy(electronicId);
        if (electronic.isOff()) throw new RuntimeException(electronic.getName() + " is already powered off");

        Electronic electronicWithStatusOff = electronic.toBuilder().status(Status.OFF).build();
        Electronic updatedElectronic = electronicRepository.update(electronicWithStatusOff);

        Consumption consumption = consumptionRepository.findLastBy(electronicId);

        Consumption consumptionWithEndTime = consumption.toBuilder()
            .endTime(LocalDateTime.now())
            .electronic(updatedElectronic)
            .build();

        Consumption updatedConsumption = consumptionRepository.update(consumptionWithEndTime);

        energyConsumptionService.stop(updatedConsumption);
    }
}
