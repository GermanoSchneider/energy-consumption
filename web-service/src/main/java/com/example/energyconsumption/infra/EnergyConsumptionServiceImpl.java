package com.example.energyconsumption.infra;

import static org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter.DataWithMediaType;
import static org.springframework.web.servlet.mvc.method.annotation.SseEmitter.event;

import com.example.energyconsumption.domain.Consumption;
import com.example.energyconsumption.domain.EnergyConsumptionService;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
class EnergyConsumptionServiceImpl implements EnergyConsumptionService {

    private final AtomicBoolean completed = new AtomicBoolean();

    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    private final Map<Long, SseEmitter> emitters;

    EnergyConsumptionServiceImpl(Map<Long, SseEmitter> emitters) {
        this.emitters = emitters;
    }

    @Override
    public void start(Consumption consumption) {

        executor.submit(() -> {

            while (!completed.get()) {

                var event = event()
                    .id(consumption.getElectronic().getId().toString())
                    .data(consumption.getKilowatts())
                    .build();

                sendEvent(event);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }


        });
    }

    @Override
    public void stop(Consumption consumption) {

        executor.shutdown();

        SseEmitter emitter = emitters.get(consumption.getElectronic().getId());
        emitter.complete();

        completed.set(true);
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
