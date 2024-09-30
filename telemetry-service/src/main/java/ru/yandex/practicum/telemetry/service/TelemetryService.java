package ru.yandex.practicum.telemetry.service;

import java.util.List;
import ru.yandex.practicum.telemetry.dto.TelemetryDto;

public interface TelemetryService {

    void saveTelemetry(List<TelemetryDto> telemetry);
}
