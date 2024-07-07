package com.example.energyconsumption.infra.persistence.consumption;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface ConsumptionJpaRepository extends JpaRepository<ConsumptionEntity, Long> {

    Collection<ConsumptionEntity> findByElectronicId(Long id);

    @Query("SELECT c FROM ConsumptionEntity c WHERE c.electronic.id = ?1 AND "
        + "c.endTime is null ORDER BY c.initialTime DESC LIMIT 1")
    ConsumptionEntity findLastBy(Long electronicId);
}
