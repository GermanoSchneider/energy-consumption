package com.example.energyconsumption.infra.persistence.electronic;

import com.example.energyconsumption.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ElectronicJpaRepository extends JpaRepository<ElectronicEntity, Long> {

    @Modifying
    @Query("UPDATE ElectronicEntity e SET e.status = ?2 WHERE e.id = ?1")
    void updateStatus(Long id, Status status);
}
