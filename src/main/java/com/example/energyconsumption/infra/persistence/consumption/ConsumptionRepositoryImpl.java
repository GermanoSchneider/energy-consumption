package com.example.energyconsumption.infra.persistence.consumption;

import com.example.energyconsumption.domain.Consumption;
import com.example.energyconsumption.domain.ConsumptionRepository;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
class ConsumptionRepositoryImpl implements ConsumptionRepository {

    private final ConsumptionJpaRepository repository;
    private final ConsumptionMapper mapper;

    ConsumptionRepositoryImpl(ConsumptionJpaRepository repository, ConsumptionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Collection<Consumption> findBy(Long electronicId) {

        return repository.findByElectronicId(electronicId)
            .stream()
            .map(mapper::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public Consumption findLastBy(Long electronicId) {

        return mapper.fromEntity(repository.findLastBy(electronicId));
    }

    @Override
    public Consumption create(Consumption consumption) {

        ConsumptionEntity entity = mapper.toEntity(consumption);

        return mapper.fromEntity(repository.save(entity));
    }

    @Override
    public Consumption update(Consumption consumption) {

        ConsumptionEntity entity = mapper.toEntity(consumption);

        return mapper.fromEntity(repository.save(entity));
    }
}
