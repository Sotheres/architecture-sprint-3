package ru.yandex.practicum.device.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.device.dto.DeviceCommandDto;
import ru.yandex.practicum.device.dto.DeviceModuleDto;
import ru.yandex.practicum.device.dto.DeviceStatusDto;
import ru.yandex.practicum.device.dto.HeatingSystemDto;
import ru.yandex.practicum.device.dto.TelemetryDto;
import ru.yandex.practicum.device.service.DeviceService;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    private final DeviceService deviceService;

    @PostMapping
    public void registerNewDevice(@RequestBody DeviceModuleDto deviceModuleDto) {
        logger.info("Registering new device");
        deviceService.registerNewDevice(deviceModuleDto);
    }

    @GetMapping("/{id}")
    public HeatingSystemDto getDeviceInfo(@PathVariable long id) {
        logger.info("Fetching info of device with id = {}", id);
        return deviceService.getDeviceInfo(id);
    }

    @PutMapping("/{id}/change-status")
    public void changeDeviceStatus(@PathVariable("id") long id, @RequestBody DeviceStatusDto statusDto) {
        logger.info("Changing status of device with id = {}", id);
        deviceService.setDeviceStatus(id, statusDto);
    }

    @PostMapping("/{id}/command")
    public void sendCommandToDevice(@PathVariable("id") long id, @RequestBody DeviceCommandDto command) {
        logger.info("Sending command to device with id = {}", id);
        deviceService.sendCommandToDevice(id, command);
    }

    @GetMapping("/{id}/current-temperature")
    public Double getCurrentTemperature(@PathVariable("id") long id) {
        logger.info("Getting current temperature of device with id = {}", id);
        return deviceService.getCurrentTemperature(id);
    }

    @GetMapping("/telemetry")
    public List<TelemetryDto> getTelemetry() {
        logger.info("Fetching device telemetry");
        return deviceService.getTelemetry();
    }
}
