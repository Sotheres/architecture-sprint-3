package ru.yandex.practicum.device.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.yandex.practicum.device.dto.DeviceCommandDto;
import ru.yandex.practicum.device.dto.DeviceCommandDto.DeviceCommandTypes;
import ru.yandex.practicum.device.dto.DeviceStatusDto;
import ru.yandex.practicum.device.entity.HeatingSystem;
import ru.yandex.practicum.device.repository.HeatingSystemRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class HeatingSystemControllerTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HeatingSystemRepository heatingSystemRepository;

    @Test
    void testGetHeatingSystem() throws Exception {
        HeatingSystem heatingSystem = new HeatingSystem();
        heatingSystem.setOn(false);
        heatingSystem.setTargetTemperature(20.0);
        heatingSystem = heatingSystemRepository.save(heatingSystem);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/devices/{id}", heatingSystem.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(heatingSystem.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.on").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.targetTemperature").value(20.0));
    }

    @Test
    void testTurnOn() throws Exception {
        HeatingSystem heatingSystem = new HeatingSystem();
        heatingSystem.setOn(false);
        heatingSystem.setTargetTemperature(20.0);
        heatingSystem = heatingSystemRepository.save(heatingSystem);

        DeviceStatusDto deviceStatusDto = new DeviceStatusDto();
        deviceStatusDto.setIsOn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/devices/{id}/change-status", heatingSystem.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deviceStatusDto)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        HeatingSystem updatedHeatingSystem = heatingSystemRepository.findById(heatingSystem.getId())
                .orElseThrow(() -> new RuntimeException("HeatingSystem not found"));
        Assertions.assertTrue(updatedHeatingSystem.isOn());
    }

    @Test
    void testSetTargetTemperature() throws Exception {
        HeatingSystem heatingSystem = new HeatingSystem();
        heatingSystem.setOn(true);
        heatingSystem.setTargetTemperature(20.0);
        heatingSystem = heatingSystemRepository.save(heatingSystem);

        DeviceCommandDto deviceCommandDto = new DeviceCommandDto();
        deviceCommandDto.setCommandType(DeviceCommandTypes.SET_TEMPERATURE);
        deviceCommandDto.setValue(23.5);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/devices/{id}/command", heatingSystem.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deviceCommandDto)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        HeatingSystem updatedHeatingSystem = heatingSystemRepository.findById(heatingSystem.getId())
                .orElseThrow(() -> new RuntimeException("HeatingSystem not found"));
        Assertions.assertEquals(23.5, updatedHeatingSystem.getTargetTemperature(), 0.01);
    }

    @Test
    void testGetCurrentTemperature() throws Exception {
        HeatingSystem heatingSystem = new HeatingSystem();
        heatingSystem.setOn(true);
        heatingSystem.setTargetTemperature(20.0);
        heatingSystem.setCurrentTemperature(19.5);
        heatingSystem = heatingSystemRepository.save(heatingSystem);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/devices/{id}/current-temperature", heatingSystem.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("19.5"));
    }
}