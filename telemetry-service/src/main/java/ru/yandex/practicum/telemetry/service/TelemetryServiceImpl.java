package ru.yandex.practicum.telemetry.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.telemetry.dto.TelemetryDto;
import ru.yandex.practicum.telemetry.entity.Telemetry;
import ru.yandex.practicum.telemetry.repository.TelemetryRepository;

@Service
@RequiredArgsConstructor
public class TelemetryServiceImpl implements TelemetryService {

    private final TelemetryRepository telemetryRepository;

    @Override
    public void saveTelemetry(List<TelemetryDto> telemetryData) {
        List<Telemetry> telemetryEntities = telemetryData.stream()
                .map(dto -> {
                    Telemetry telemetry = new Telemetry();
                    telemetry.setDeviceId(dto.getDeviceId());
                    telemetry.setCurrentTemperature(dto.getCurrentTemperature());
                    return telemetry;
                }).toList();
        telemetryRepository.saveAll(telemetryEntities);
    }
}
