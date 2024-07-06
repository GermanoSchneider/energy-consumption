package com.example.energyconsumption.infra;

import static org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter.DataWithMediaType;
import static org.springframework.web.servlet.mvc.method.annotation.SseEmitter.event;

import com.example.energyconsumption.domain.Consumption;
import com.example.energyconsumption.domain.EnergyConsumptionService;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
class EnergyConsumptionServiceImpl implements EnergyConsumptionService {

    private final Map<Long, SseEmitter> emitters;

    EnergyConsumptionServiceImpl(Map<Long, SseEmitter> emitters) {
        this.emitters = emitters;
    }

    @Override
    public void start(Consumption consumption) {

        var event = event()
                .data(consumption.getKilowatts())
                .name(consumption.getId().toString())
                .build();

        sendEvent(event);
    }

    @Override
    public void stop(Consumption consumption) {

        SseEmitter emitter = emitters.get(consumption.getId());
        emitter.complete();
    }

    private void sendEvent(Set<DataWithMediaType> event) {

        emitters.forEach((id, emitter) -> {
            try {
                emitter.send(event);
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        });
    }
}
