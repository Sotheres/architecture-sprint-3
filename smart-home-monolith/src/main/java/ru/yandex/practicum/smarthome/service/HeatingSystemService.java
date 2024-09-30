package ru.yandex.practicum.smarthome.service;

import ru.yandex.practicum.smarthome.dto.DeviceStatusDto;

public interface HeatingSystemService {

    void changeStatus(long id, DeviceStatusDto status);
}