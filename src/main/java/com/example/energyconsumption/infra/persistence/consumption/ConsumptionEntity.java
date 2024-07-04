package com.example.energyconsumption.infra.persistence.consumption;

import com.example.energyconsumption.infra.persistence.electronic.ElectronicEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Double kilowatts;

    @Column(name = "initial_time")
    private LocalDateTime initialTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @ManyToOne
    private ElectronicEntity electronic;
}
