package com.example.energyconsumption.domain;

import java.util.Collection;

public interface ElectronicRepository {

    Collection<Electronic> findAll();
}
