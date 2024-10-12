package ru.yandex.practicum.device.service;

import java.util.List;
import ru.yandex.practicum.device.dto.DeviceCommandDto;
import ru.yandex.practicum.device.dto.DeviceModuleDto;
import ru.yandex.practicum.device.dto.DeviceStatusDto;
import ru.yandex.practicum.device.dto.HeatingSystemDto;
import ru.yandex.practicum.device.dto.TelemetryDto;

public interface DeviceService {

    HeatingSystemDto getDeviceInfo(long id);

    void setDeviceStatus(long id, DeviceStatusDto statusDto);

    void sendCommandToDevice(long id, DeviceCommandDto command);

    void registerNewDevice(DeviceModuleDto deviceModuleDto);

    List<TelemetryDto> getTelemetry();

    Double getCurrentTemperature(long id);
}
