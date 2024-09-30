package ru.yandex.practicum.device.dto;

import lombok.Data;

@Data
public class DeviceModuleDto {

    private Long id;
    private String name;
    private String description;
    private String serialNumber;
}
