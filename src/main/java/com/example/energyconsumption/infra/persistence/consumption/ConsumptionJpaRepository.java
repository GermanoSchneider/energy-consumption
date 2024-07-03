package com.example.energyconsumption.infra.persistence.consumption;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

interface ConsumptionJpaRepository extends JpaRepository<ConsumptionEntity, Long> {
    Collection<ConsumptionEntity> findByElectronicId(Long id);
}
