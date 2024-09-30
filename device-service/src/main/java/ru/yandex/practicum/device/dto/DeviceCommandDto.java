package ru.yandex.practicum.device.dto;

import lombok.Data;

@Data
public class DeviceCommandDto {

    private DeviceCommandTypes commandType;
    private Double value;

    public enum DeviceCommandTypes {
        SET_TEMPERATURE
    }
}
