package com.example.energy_consumption.infra.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_consumption")
public class ConsumptionEntity {

    @Id
    private Long id;

    private Double kilowatts;

    @Column(name = "creation_date")
    private LocalDate date;

    @ManyToOne
    private ElectronicEntity electronic;
}
