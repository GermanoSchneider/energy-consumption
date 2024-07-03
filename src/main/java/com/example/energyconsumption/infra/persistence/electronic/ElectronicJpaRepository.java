package com.example.energyconsumption.infra.persistence.electronic;

import org.springframework.data.jpa.repository.JpaRepository;

interface ElectronicJpaRepository extends JpaRepository<ElectronicEntity, Long> { }
