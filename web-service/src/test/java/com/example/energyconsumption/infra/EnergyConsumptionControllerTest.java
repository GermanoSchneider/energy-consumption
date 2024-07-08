package com.example.energyconsumption.infra;

import static java.lang.Long.MAX_VALUE;
import static java.lang.Long.valueOf;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.energyconsumption.application.EnergyConsumptionApplicationService;
import com.example.energyconsumption.domain.Consumption;
import com.example.energyconsumption.domain.ConsumptionFixture;
import com.example.energyconsumption.domain.Electronic;
import com.example.energyconsumption.domain.ElectronicFixture;
import com.example.energyconsumption.infra.persistence.consumption.ConsumptionDto;
import com.example.energyconsumption.infra.persistence.consumption.ConsumptionMapper;
import com.example.energyconsumption.infra.persistence.electronic.ElectronicDto;
import com.example.energyconsumption.infra.persistence.electronic.ElectronicMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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

    @SpyBean
    private ConsumptionMapper consumptionMapper;

    @SpyBean
    private ElectronicMapper electronicMapper;

    @Captor
    private ArgumentCaptor<SseEmitter> emitterArgumentCaptor;

    private ObjectMapper mapper;

    @BeforeEach
    void init() {

        mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .registerModule(new Jdk8Module());
    }

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

    @Test
    @DisplayName("should find all electronics and return with their consumptions")
    void shouldFindAllElectronicsAndReturnWithConsumptions() throws Exception {

        Electronic computer = ElectronicFixture.build();
        Long electronicId = computer.getId();

        Consumption consumption = ConsumptionFixture.build();

        Collection<Electronic> electronics = List.of(computer);

        Collection<Consumption> consumptions = List.of(consumption);

        Collection<ConsumptionDto> consumptionsDTO = List.of(toConsumptionDto(consumption));

        doReturn(electronics)
            .when(applicationService)
            .getAllElectronics();

        doReturn(consumptions)
            .when(applicationService)
            .getAllConsumptionsBy(electronicId);

        Collection<ElectronicDto> expectedElectronics = electronics.stream()
            .map(electronic -> toElectronicDto(electronic, consumptionsDTO))
            .toList();

        String expectedJson = mapper.writeValueAsString(expectedElectronics);

        MvcResult result = mockMvc.perform(
            get("/electronics")
        ).andReturn();

        int statusCode = result.getResponse().getStatus();
        String resultResponse = result.getResponse().getContentAsString();

        Assertions.assertThat(HttpStatus.OK.value()).isEqualTo(statusCode);
        Assertions.assertThat(expectedJson).isEqualTo(resultResponse);

        Mockito.verify(applicationService).getAllElectronics();
        Mockito.verify(applicationService).getAllConsumptionsBy(electronicId);
        Mockito.verify(consumptionMapper).toConsumptionDto(consumption);
        Mockito.verify(electronicMapper).toElectronicDto(computer, consumptionsDTO);
    }

    @Test
    @DisplayName("should not find any electronic")
    void shouldNotFindAnyElectronic() throws Exception {

        doReturn(List.of())
            .when(applicationService)
            .getAllElectronics();

        MvcResult result = mockMvc.perform(
            get("/electronics")
        ).andReturn();

        int statusCode = result.getResponse().getStatus();

        Assertions.assertThat(HttpStatus.NO_CONTENT.value()).isEqualTo(statusCode);

        Mockito.verify(applicationService).getAllElectronics();
    }

    private ConsumptionDto toConsumptionDto(Consumption consumption) {

        return new ConsumptionDto(
            consumption.getId(),
            consumption.getInitialTime(),
            consumption.getEndTime()
        );
    }

    private ElectronicDto toElectronicDto(
        Electronic electronic,
        Collection<ConsumptionDto> consumptions
    ) {

        return new ElectronicDto(
            electronic.getId(),
            electronic.getName(),
            electronic.getStatus(),
            consumptions
        );
    }
}
