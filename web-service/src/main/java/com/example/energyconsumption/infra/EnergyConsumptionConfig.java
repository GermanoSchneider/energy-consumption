package com.example.energyconsumption.infra;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Configuration
class EnergyConsumptionConfig {

    @Bean
    Collection<SseEmitter> emitters() {
        return new CopyOnWriteArrayList<>();
    }
}
