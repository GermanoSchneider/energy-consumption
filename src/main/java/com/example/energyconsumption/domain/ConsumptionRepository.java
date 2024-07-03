package com.example.energyconsumption.domain;

import java.util.Collection;

public interface ConsumptionRepository {

    Collection<Consumption> findBy(Long electronicId);

    Consumption create(Consumption consumption);
}
