package ru.yandex.practicum.device.dto;

import lombok.Data;

@Data
public class HeatingSystemDto {

    private Long id;
    private boolean isOn;
    private double targetTemperature;
    private double currentTemperature;
    private long deviceModuleId;
}