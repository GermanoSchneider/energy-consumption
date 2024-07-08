package com.example.energyconsumption.domain;

public interface EnergyConsumptionService {

    void start(Consumption consumption);

    void stop(Consumption consumption);

}
