package ru.yandex.practicum.smarthome.dto;

import lombok.Data;

@Data
public class TelemetryDto {

    private Long id;
    private long deviceId;
    private double currentTemperature;
}
