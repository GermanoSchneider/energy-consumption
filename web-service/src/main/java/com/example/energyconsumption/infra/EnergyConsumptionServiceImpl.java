package com.example.energyconsumption.infra;

import static org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter.DataWithMediaType;
import static org.springframework.web.servlet.mvc.method.annotation.SseEmitter.event;

import com.example.energyconsumption.domain.Consumption;
import com.example.energyconsumption.domain.EnergyConsumptionService;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
class EnergyConsumptionServiceImpl implements EnergyConsumptionService {

    private final ConcurrentHashMap<Long, Boolean> completed = new ConcurrentHashMap<>();

    private final Collection<SseEmitter> emitters;

    EnergyConsumptionServiceImpl(Collection<SseEmitter> emitters) {
        this.emitters = emitters;
    }

    @Override
    public void start(Consumption consumption) {

        completed.put(consumption.getId(), false);

        new Thread(() -> {

            while (!completed.get(consumption.getId())) {

                var event = event()
                    .id(consumption.getElectronic().getId().toString())
                    .data(consumption.getKilowatts())
                    .build();

                sendEvent(event);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            completed.remove(consumption.getId());

        }).start();
    }

    @Override
    public void stop(Consumption consumption) {
        completed.put(consumption.getId(), true);
    }

    private void sendEvent(Set<DataWithMediaType> event)  {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
