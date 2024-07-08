package com.example.energyconsumption.infra.persistence.electronic;

import com.example.energyconsumption.domain.Electronic;
import com.example.energyconsumption.domain.ElectronicRepository;
import com.example.energyconsumption.infra.ElectronicMapper;
import java.util.Collection;
import java.util.Optional;
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
    public Electronic findBy(Long id) {

        Optional<ElectronicEntity> entity = repository.findById(id);

        if (entity.isEmpty()) throw new RuntimeException("Electronic with id " + id + " does not exist");

        return mapper.fromEntity(entity.get());
    }

    @Override
    public Electronic update(Electronic electronic) {

        ElectronicEntity entity = mapper.toEntity(electronic);

        return mapper.fromEntity(repository.save(entity));
    }
}
