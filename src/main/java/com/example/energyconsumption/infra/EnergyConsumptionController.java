package com.example.energyconsumption.infra;

import static java.lang.Long.MAX_VALUE;

import com.example.energyconsumption.application.EnergyConsumptionApplicationService;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
class EnergyConsumptionController {

    private final EnergyConsumptionApplicationService application;
    private final Map<Long, SseEmitter> emitters;

    EnergyConsumptionController(
        Map<Long, SseEmitter> emitters,
        EnergyConsumptionApplicationService application
    ) {
        this.emitters = emitters;
        this.application = application;
    }

    @GetMapping(path = "/open-sse/{electronicId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    ResponseEntity<SseEmitter> openSse(@PathVariable String electronicId) {

        SseEmitter emitter = new SseEmitter(MAX_VALUE);

        Long id = Long.valueOf(electronicId);

        emitter.onCompletion(() -> emitters.remove(id));
        emitter.onTimeout(() -> emitters.remove(id));
        emitter.onError(throwable -> {
            emitters.remove(id);
            emitter.completeWithError(throwable);
        });

        emitters.put(id, emitter);

        return ResponseEntity.ok(emitter);
    }

    @GetMapping(value = "/power-on/{electronicId}")
    void powerOn(@PathVariable String electronicId) {

        application.powerOn(Long.valueOf(electronicId));
    }

    @GetMapping(value = "/power-off/{electronicId}")
    void powerOff(@PathVariable String electronicId) {

        application.powerOff(Long.valueOf(electronicId));
    }
}
