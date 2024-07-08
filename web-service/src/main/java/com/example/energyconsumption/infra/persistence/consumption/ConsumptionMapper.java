package com.example.energyconsumption.infra.persistence.consumption;

import com.example.energyconsumption.domain.Consumption;
import com.example.energyconsumption.infra.persistence.electronic.ElectronicMapper;
import org.springframework.stereotype.Component;

@Component
public class ConsumptionMapper {

    private final ElectronicMapper mapper;

    public ConsumptionMapper(ElectronicMapper mapper) {
        this.mapper = mapper;
    }

    public Consumption fromEntity(ConsumptionEntity entity) {

        return Consumption.builder()
            .id(entity.getId())
            .initialTime(entity.getInitialTime())
            .endTime(entity.getEndTime())
            .electronic(mapper.fromEntity(entity.getElectronic()))
            .build();
    }

    public ConsumptionEntity toEntity(Consumption consumption) {

        return new ConsumptionEntity(
          consumption.getId(),
          consumption.getInitialTime(),
          consumption.getEndTime(),
          mapper.toEntity(consumption.getElectronic())
        );
    }

    public ConsumptionDto toConsumptionDto(Consumption consumption) {

        return new ConsumptionDto(
            consumption.getId(),
            consumption.getInitialTime(),
            consumption.getEndTime()
        );
    }
}
