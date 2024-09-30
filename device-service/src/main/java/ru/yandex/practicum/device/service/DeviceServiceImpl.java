package ru.yandex.practicum.device.service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.device.dto.DeviceCommandDto;
import ru.yandex.practicum.device.dto.DeviceCommandDto.DeviceCommandTypes;
import ru.yandex.practicum.device.dto.DeviceModuleDto;
import ru.yandex.practicum.device.dto.DeviceStatusDto;
import ru.yandex.practicum.device.dto.HeatingSystemDto;
import ru.yandex.practicum.device.dto.TelemetryDto;
import ru.yandex.practicum.device.entity.DeviceModule;
import ru.yandex.practicum.device.entity.HeatingSystem;
import ru.yandex.practicum.device.exception.NotFoundException;
import ru.yandex.practicum.device.repository.DeviceModuleRepository;
import ru.yandex.practicum.device.repository.HeatingSystemRepository;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private static final String HEATING_SYSTEM_NOT_FOUND = "HeatingSystem not found";

    private final TemperatureSensorSystem temperatureSensorSystem;
    private final HeatingSystemRepository heatingSystemRepository;
    private final DeviceModuleRepository deviceModuleRepository;

    @Scheduled(fixedRate = 15, timeUnit = TimeUnit.SECONDS)
    public void fetchCurrentTemperature() {
        List<HeatingSystem> devices = heatingSystemRepository.findAll();
        devices.stream()
                .filter(device -> device.getId() != null)
                .forEach(device -> {
                    double currentTemperature = temperatureSensorSystem.getCurrentTemperature(device.getId());
                    device.setCurrentTemperature(currentTemperature);
                    device.setOn(!isTemperatureReached(device));
                    heatingSystemRepository.save(device);
                });
    }

    private boolean isTemperatureReached(HeatingSystem device) {
        double targetTemp = device.getTargetTemperature();
        double currTemp = device.getCurrentTemperature();
        if (currTemp > targetTemp) {
            return true;
        }
        return Double.compare(currTemp, targetTemp) == 0;
    }

    @Override
    public void registerNewDevice(DeviceModuleDto deviceModuleDto) {
        DeviceModule deviceModule = new DeviceModule();
        deviceModule.setName(deviceModuleDto.getName());
        deviceModule.setDescription(deviceModuleDto.getDescription());
        deviceModule.setSerialNumber(deviceModuleDto.getSerialNumber());
        DeviceModule persistedModule = deviceModuleRepository.save(deviceModule);

        HeatingSystem heatingSystem = new HeatingSystem();
        heatingSystem.setDeviceModuleId(persistedModule.getId());
        heatingSystemRepository.save(heatingSystem);
    }

    @Override
    public HeatingSystemDto getDeviceInfo(long id) {
        HeatingSystem heatingSystem = heatingSystemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(HEATING_SYSTEM_NOT_FOUND));
        return convertToDto(heatingSystem);
    }

    private HeatingSystemDto convertToDto(HeatingSystem heatingSystem) {
        HeatingSystemDto dto = new HeatingSystemDto();
        dto.setId(heatingSystem.getId());
        dto.setOn(heatingSystem.isOn());
        dto.setTargetTemperature(heatingSystem.getTargetTemperature());
        dto.setCurrentTemperature(heatingSystem.getCurrentTemperature());
        dto.setDeviceModuleId(heatingSystem.getDeviceModuleId());

        return dto;
    }

    @Override
    public void setDeviceStatus(long id, DeviceStatusDto statusDto) {
        HeatingSystem device = heatingSystemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(HEATING_SYSTEM_NOT_FOUND));
        device.setOn(statusDto.getIsOn());
        heatingSystemRepository.save(device);
    }

    @Override
    public void sendCommandToDevice(long id, DeviceCommandDto command) {
        if (DeviceCommandTypes.SET_TEMPERATURE == command.getCommandType()) {
            HeatingSystem device = heatingSystemRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(HEATING_SYSTEM_NOT_FOUND));
            device.setTargetTemperature(command.getValue());
            heatingSystemRepository.save(device);
        }
    }

    @Override
    public List<TelemetryDto> getTelemetry() {
        return heatingSystemRepository.findAll().stream()
                .filter(device -> device.getId() != null)
                .map(device -> {
                    TelemetryDto telemetryDto = new TelemetryDto();
                    telemetryDto.setDeviceId(device.getId());
                    telemetryDto.setCurrentTemperature(device.getCurrentTemperature());
                    return telemetryDto;
                }).toList();
    }

    @Override
    public Double getCurrentTemperature(long id) {
        HeatingSystem device = heatingSystemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(HEATING_SYSTEM_NOT_FOUND));
        return device.getCurrentTemperature();
    }
}
