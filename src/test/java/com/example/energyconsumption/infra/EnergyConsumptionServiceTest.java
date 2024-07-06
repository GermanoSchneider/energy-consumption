package com.example.energyconsumption.infra;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import com.example.energyconsumption.domain.Consumption;
import com.example.energyconsumption.domain.ConsumptionFixture;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@ExtendWith(SpringExtension.class)
class EnergyConsumptionServiceTest {

    @InjectMocks
    private EnergyConsumptionServiceImpl energyConsumptionService;

    @Mock
    private Map<Long, SseEmitter> emitters;

    private final SseEmitter sseEmitter = new SseEmitter();

    @BeforeEach
    void init() {
        emitters.put(1L, sseEmitter);
    }

    private final Consumption consumption = ConsumptionFixture.build();

    @Test
    @DisplayName("should start to send events when electronic is powered on")
    void shouldCreateANewEmitterToSendConsumptionKilowattsWhenElectronicIsPoweredOn() {

        doNothing()
                .when(emitters)
                .forEach(any());

        energyConsumptionService.start(consumption);

        Mockito.verify(emitters).forEach(any());
    }

    @Test
    @DisplayName("should stop to send events when the electronic is powered off")
    void shouldRemoveAExistingEmitterWhenElectronicIsPoweredOff() {

        doReturn(sseEmitter)
            .when(emitters)
            .get(consumption.getId());

        energyConsumptionService.stop(consumption);

        Mockito.verify(emitters).get(consumption.getId());
    }
}
