package com.example.energyconsumption.infra;

import com.example.energyconsumption.domain.Status;
import java.util.Collection;

public record ElectronicDto(

    Long id,

    String name,

    Status status,

    Collection<ConsumptionDto> consumptions

) {}