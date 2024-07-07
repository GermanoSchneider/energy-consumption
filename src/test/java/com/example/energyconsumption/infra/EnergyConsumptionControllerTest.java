package com.example.energyconsumption.infra;

import static java.lang.Long.MAX_VALUE;
import static java.lang.Long.valueOf;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.energyconsumption.application.EnergyConsumptionApplicationService;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@WebMvcTest(EnergyConsumptionController.class)
class EnergyConsumptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnergyConsumptionApplicationService applicationService;

    @MockBean
    private Map<Long, SseEmitter> emitters;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @Captor
    private ArgumentCaptor<SseEmitter> emitterArgumentCaptor;

    @Test
    @DisplayName("should open SSE connection")
    void shouldOpenSseConnection() throws Exception {

        String electronicId = "1";
        Long id = Long.valueOf(electronicId);
        SseEmitter emitter = new SseEmitter(MAX_VALUE);

        doReturn(emitter)
            .when(emitters)
            .put(id, emitter);

        mockMvc.perform(
            get("/open-sse/" + electronicId)
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("should call the /power-on endpoint")
    void shouldCallPowerOnEndpoint() throws Exception {

        String electronicId = "1";

        doNothing()
                .when(applicationService)
                .powerOn(valueOf(electronicId));

        mockMvc.perform(
            get("/power-on/" + electronicId)
        ).andExpect(status().isOk());

        verify(applicationService).powerOn(valueOf(electronicId));
    }

    @Test
    @DisplayName("should call the /power-off endpoint")
    void shouldCallPowerOffEndpoint() throws Exception {

        String electronicId = "1";

        doNothing()
            .when(applicationService)
            .powerOff(valueOf(electronicId));

        mockMvc.perform(
            get("/power-off/" + electronicId)
        ).andExpect(status().isOk());

        verify(applicationService).powerOff(valueOf(electronicId));
    }
}
