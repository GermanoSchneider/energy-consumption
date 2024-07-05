package com.example.energyconsumption.domain;

import java.util.Collection;
import java.util.Optional;

public interface ElectronicRepository {

    Collection<Electronic> findAll();

    Optional<Electronic> findBy(Long id);
}
