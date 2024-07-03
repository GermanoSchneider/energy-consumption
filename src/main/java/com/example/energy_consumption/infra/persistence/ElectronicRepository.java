package com.example.energy_consumption.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectronicRepository extends JpaRepository<ElectronicEntity, Long> { }
