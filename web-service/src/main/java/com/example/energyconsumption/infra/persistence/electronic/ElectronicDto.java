package com.example.energyconsumption.infra.persistence.electronic;

import com.example.energyconsumption.domain.Status;
import com.example.energyconsumption.infra.persistence.consumption.ConsumptionDto;
import java.util.Collection;

public record ElectronicDto(

    Long id,

    String name,

    Double power,

    Status status,

    Collection<ConsumptionDto> consumptions

) {}