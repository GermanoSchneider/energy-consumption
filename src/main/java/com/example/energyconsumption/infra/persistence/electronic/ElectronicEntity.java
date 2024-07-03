package com.example.energyconsumption.infra.persistence.electronic;

import static jakarta.persistence.EnumType.STRING;

import com.example.energyconsumption.domain.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_electronic")
public class ElectronicEntity {

    @Id
    private Long id;

    private String name;

    private double power;

    @Enumerated(STRING)
    private Status status;
}
