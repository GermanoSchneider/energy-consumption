package com.example.energyconsumption.infra;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.ReflectionTestUtils.getField;

import com.example.energyconsumption.domain.Consumption;
import com.example.energyconsumption.domain.ConsumptionFixture;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class EnergyConsumptionServiceTest {

    @InjectMocks
    private EnergyConsumptionServiceImpl energyConsumptionService;

    @Mock
    private Map<Long, SseEmitter> emitters;

    private final SseEmitter sseEmitter = new SseEmitter();

    private AtomicBoolean completed;

    @BeforeEach
    void init() {
        completed = (AtomicBoolean) getField(energyConsumptionService, "completed");
        emitters.put(1L, sseEmitter);
    }

    private final Consumption consumption = ConsumptionFixture.build();

    @Test
    @Order(1)
    @DisplayName("should start to send events when electronic is powered on")
    void shouldCreateANewEmitterToSendConsumptionKilowattsWhenElectronicIsPoweredOn() {

        energyConsumptionService.start(consumption);

        assertFalse(completed.get());
    }

    @Test
    @Order(2)
    @DisplayName("should stop to send events when the electronic is powered off")
    void shouldRemoveAExistingEmitterWhenElectronicIsPoweredOff() {

        doReturn(sseEmitter)
            .when(emitters)
            .get(consumption.getId());

        energyConsumptionService.stop(consumption);

        assertTrue(completed.get());

        verify(emitters).get(consumption.getId());
    }
}
