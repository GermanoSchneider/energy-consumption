package com.example.energyconsumption.infra.persistence.electronic;

import com.example.energyconsumption.domain.Electronic;
import com.example.energyconsumption.domain.ElectronicRepository;
import com.example.energyconsumption.domain.Status;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
class ElectronicRepositoryImpl implements ElectronicRepository {

    private final ElectronicJpaRepository repository;
    private final ElectronicMapper mapper;

    ElectronicRepositoryImpl(ElectronicJpaRepository repository, ElectronicMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Collection<Electronic> findAll() {

        return repository.findAll()
            .stream()
            .map(mapper::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public void updateStatus(Long id, Status status) {
        repository.updateStatus(id, status);
    }
}
