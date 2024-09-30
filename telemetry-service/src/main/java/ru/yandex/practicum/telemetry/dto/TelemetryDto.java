package ru.yandex.practicum.telemetry.dto;

import lombok.Data;

@Data
public class TelemetryDto {

    private Long id;
    private long deviceId;
    private double currentTemperature;
}
