package com.example.energyconsumption.application;

import com.example.energyconsumption.domain.Consumption;
import com.example.energyconsumption.domain.ConsumptionRepository;
import com.example.energyconsumption.domain.Electronic;
import com.example.energyconsumption.domain.ElectronicRepository;
import com.example.energyconsumption.domain.EnergyConsumptionService;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ElectronicApplicationService {

    private final ElectronicRepository electronicRepository;

    private final ConsumptionRepository consumptionRepository;

    private final EnergyConsumptionService energyConsumptionService;

    public ElectronicApplicationService(
        ElectronicRepository electronicRepository,
        ConsumptionRepository consumptionRepository,
        EnergyConsumptionService energyConsumptionService
    ) {
        this.electronicRepository = electronicRepository;
        this.consumptionRepository = consumptionRepository;
        this.energyConsumptionService = energyConsumptionService;
    }

    public void powerOn(Long electronicId) {

        Optional<Electronic> electronicOptional = electronicRepository.findBy(electronicId);

        if (electronicOptional.isPresent()) {
            Electronic electronic = electronicOptional.get();
            if (electronic.isOn()) throw new RuntimeException(electronic.getName() + " is already powered on");
            Consumption consumption = consumptionRepository.create(buildConsumption(electronic));
            energyConsumptionService.start(consumption);
        } else {
            throw new RuntimeException("Electronic device does not exist");
        }
    }

    private Consumption buildConsumption(Electronic electronic) {

        return Consumption.builder()
            .initialTime(LocalDateTime.now())
            .electronic(electronic)
            .build();
    }

}
