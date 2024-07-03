package com.example.energyconsumption.infra.persistence.electronic;

import com.example.energyconsumption.domain.Electronic;
import org.springframework.stereotype.Component;

@Component
public class ElectronicMapper {

    public Electronic fromEntity(ElectronicEntity entity) {

        return Electronic.builder()
            .id(entity.getId())
            .name(entity.getName())
            .power(entity.getPower())
            .status(entity.getStatus())
            .build();
    }

    public ElectronicEntity toEntity(Electronic consumption) {

        return new ElectronicEntity(
          consumption.getId(),
          consumption.getName(),
          consumption.getPower(),
          consumption.getStatus()
        );
    }
}
