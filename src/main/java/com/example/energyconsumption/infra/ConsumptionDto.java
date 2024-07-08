package com.example.energyconsumption.infra;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public record ConsumptionDto(
    Long id,
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    LocalDateTime initialTime,
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    LocalDateTime endTime
) { }
