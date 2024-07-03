package com.example.energy_consumption.infra.persistence;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumptionRepository extends JpaRepository<ConsumptionEntity, Long> {
    Collection<ConsumptionEntity> findByElectronicId(Long id);
}
