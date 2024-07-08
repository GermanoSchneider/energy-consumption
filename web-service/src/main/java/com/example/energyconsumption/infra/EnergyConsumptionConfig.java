package com.example.energyconsumption.infra;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Configuration
class EnergyConsumptionConfig {

    @Bean
    Map<Long, SseEmitter> emitters() {
        return new ConcurrentHashMap<>();
    }
}
